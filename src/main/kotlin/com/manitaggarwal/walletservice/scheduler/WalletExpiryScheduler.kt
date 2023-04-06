package com.manitaggarwal.walletservice.scheduler

import com.manitaggarwal.walletservice.dao.WalletRepository
import com.manitaggarwal.walletservice.dao.WalletSummaryRepository
import com.manitaggarwal.walletservice.domain.Wallet
import com.manitaggarwal.walletservice.domain.WalletStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import kotlin.properties.Delegates

@Component
class WalletExpiryScheduler {


    @Autowired
    lateinit var walletRepository: WalletRepository

    @Autowired
    lateinit var walletSummaryRepository: WalletSummaryRepository

    var logger: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(WalletExpiryScheduler::class.java)

    @Scheduled(cron = "\${wallet.expiry.cron}")
    fun expireWallets() {
        logger.info("Expiring wallets")
        walletRepository.findWalletByExpiryDateBefore(LocalDateTime.now())
            .map { wallet ->
                wallet.walletStatus = WalletStatus.EXPIRED
                wallet.expired = wallet.earned - wallet.spent
                walletRepository.save(wallet)
            }.doOnNext(this::updateSummary)
            .subscribe()
    }

    private fun updateSummary(mono: Mono<Wallet>?) {
        mono?.let {
            it.subscribe { wallet ->
                walletSummaryRepository
                    .findWalletSummaryByUserIdAndProgram(wallet.userId, wallet.program)
                    .flatMap { walletSummary ->
                        walletSummary.balance -= wallet.expired
                        walletSummaryRepository.save(walletSummary)
                    }.subscribe()
            }
        }
    }
}