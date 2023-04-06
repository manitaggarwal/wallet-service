package com.manitaggarwal.walletservice.service

import com.manitaggarwal.walletservice.domain.Wallet
import com.manitaggarwal.walletservice.domain.WalletSummary
import reactor.core.publisher.Flux

interface UserService {
    fun getUserWallets(userId: String): Flux<Wallet>
    fun getUserWalletSummaries(userId: String): Flux<WalletSummary>
}