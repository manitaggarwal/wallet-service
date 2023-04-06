package com.manitaggarwal.walletservice.service

import com.manitaggarwal.walletservice.controller.request.ConsumeWalletRequest
import com.manitaggarwal.walletservice.controller.request.CreateWalletRequest
import com.manitaggarwal.walletservice.dao.WalletRepository
import com.manitaggarwal.walletservice.dao.WalletSummaryRepository
import com.manitaggarwal.walletservice.domain.Program
import com.manitaggarwal.walletservice.domain.Wallet
import com.manitaggarwal.walletservice.domain.WalletStatus
import com.manitaggarwal.walletservice.domain.WalletSummary
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.LocalDateTime

@Service
class WalletServiceImpl : WalletService {


    @Autowired
    lateinit var walletRepository: WalletRepository

    @Autowired
    lateinit var walletSummaryRepository: WalletSummaryRepository

    override fun createWallet(
        createWalletRequest: CreateWalletRequest,
        program: Program
    ): Mono<Wallet> {
        validateCreateWalletRequest(createWalletRequest)
        return walletRepository.save(
            Wallet(
                createWalletRequest.userId,
                createWalletRequest.earned,
                createWalletRequest.spent,
                0,
                WalletStatus.ACTIVE,
                program.id,
                createWalletRequest.expiryDate,
                createWalletRequest.transactionId
            )
        ).doOnSuccess(this::summarizeWallet)
            .onErrorResume(DuplicateKeyException::class.java) {
                Mono.error(
                    IllegalArgumentException(
                        "Wallet already exists for transaction ${createWalletRequest.transactionId}"
                    )
                )
            }
    }

    fun summarizeWallet(wallet: Wallet) {
        walletSummaryRepository
            .findWalletSummaryByUserIdAndProgram(wallet.userId, wallet.program)
            .flatMap { walletSummary ->
                walletSummary.balance += (wallet.earned - wallet.spent)
                walletSummaryRepository.save(walletSummary)
            }
            .switchIfEmpty(
                walletSummaryRepository.save(
                    WalletSummary(
                        wallet.userId,
                        wallet.earned - wallet.spent,
                        WalletStatus.ACTIVE,
                        wallet.program
                    )
                )
            )
            .subscribe()
    }

    override fun consumeWallet(
        consumeWalletRequest: ConsumeWalletRequest,
        program: Program
    ): Flux<Wallet> {
        validateConsumeWalletRequest(consumeWalletRequest, program)
        return walletRepository.findByUserIdAndProgramAndWalletStatusOrderById(
            consumeWalletRequest.userId,
            program.id,
            WalletStatus.ACTIVE
        ).collectList()
            .flatMapMany { wallets ->
                val consumedAmount = consumeWalletRequest.amount
                var remainingAmount = consumedAmount
                val consumedWallets = mutableListOf<Wallet>()
                wallets.forEach { wallet ->
                    if (remainingAmount > 0) {

                        val amountToConsume =
                            remainingAmount.coerceAtMost((wallet.earned - wallet.spent))
                        wallet.spent += amountToConsume
                        if (wallet.spent == wallet.earned) {
                            wallet.walletStatus = WalletStatus.INACTIVE
                        }
                        remainingAmount -= amountToConsume
                        consumedWallets.add(wallet)
                    }
                }
                if (remainingAmount > 0) {
                    throw IllegalArgumentException("Insufficient balance")
                }
                walletRepository.saveAll(consumedWallets)
            }.doOnComplete { updateSummaryWallet(consumeWalletRequest, program) }
    }

    private fun updateSummaryWallet(consumeWalletRequest: ConsumeWalletRequest, program: Program) {
        walletSummaryRepository.findWalletSummaryByUserIdAndProgram(
            consumeWalletRequest.userId,
            program.id
        ).flatMap { walletSummary ->
            walletSummary.balance -= consumeWalletRequest.amount
            walletSummaryRepository.save(walletSummary)
        }.switchIfEmpty {
            Mono.error(IllegalStateException("WalletSummary not found"))
        }.subscribe()
    }

    private fun validateConsumeWalletRequest(
        consumeWalletRequest: ConsumeWalletRequest,
        program: Program
    ) {

        // amount to consume cannot be negative or zero
        if (consumeWalletRequest.amount <= 0) {
            throw IllegalArgumentException("Amount to consume cannot be negative or zero")
        }

        // get balance from wallet summary and check if the transaction can be completed
        walletSummaryRepository
            .findWalletSummaryByUserIdAndProgram(
                consumeWalletRequest.userId,
                program.id
            )
            .doOnNext { walletSummary ->
                if (walletSummary.balance < consumeWalletRequest.amount) {
                    throw IllegalArgumentException("Insufficient balance")
                }
            }
            .switchIfEmpty {
                Mono.error(IllegalStateException("WalletSummary not found"))
            }
            .subscribe()


    }

    private fun validateCreateWalletRequest(createWalletRequest: CreateWalletRequest) {

        // cannot be negative
        if (createWalletRequest.earned < 0) {
            throw IllegalArgumentException("Earned cannot be negative")
        }
        if (createWalletRequest.spent < 0) {
            throw IllegalArgumentException("Spent cannot be negative")
        }

        // earned cannot be less than spent
        if (createWalletRequest.earned < createWalletRequest.spent) {
            throw IllegalArgumentException("Earned cannot be less than spent")
        }

        // expiry date cannot be in the past
        if (createWalletRequest.expiryDate.isBefore(LocalDateTime.now())) {
            throw IllegalArgumentException("Expiry date cannot be in the past")
        }

    }
}
