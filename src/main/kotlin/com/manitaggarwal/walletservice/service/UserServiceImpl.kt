package com.manitaggarwal.walletservice.service

import com.manitaggarwal.walletservice.dao.WalletRepository
import com.manitaggarwal.walletservice.dao.WalletSummaryRepository
import com.manitaggarwal.walletservice.domain.Wallet
import com.manitaggarwal.walletservice.domain.WalletStatus
import com.manitaggarwal.walletservice.domain.WalletSummary
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var walletRepository: WalletRepository

    @Autowired
    lateinit var walletSummaryRepository: WalletSummaryRepository

    override fun getUserWallets(userId: String): Flux<Wallet> {
        return walletRepository.findByUserIdAndWalletStatusOrderById(userId, WalletStatus.ACTIVE)
            .switchIfEmpty(Mono.error(IllegalArgumentException("You have not started earning yet")))
    }

    override fun getUserWalletSummaries(userId: String): Flux<WalletSummary> {
        return walletSummaryRepository.findByUserId(userId)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Wallet summary do not exist for this user")))
    }
}