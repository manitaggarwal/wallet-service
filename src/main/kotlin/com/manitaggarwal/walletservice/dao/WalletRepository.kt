package com.manitaggarwal.walletservice.dao

import com.manitaggarwal.walletservice.domain.Wallet
import com.manitaggarwal.walletservice.domain.WalletStatus
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@Repository
interface WalletRepository : R2dbcRepository<Wallet, Long> {

    fun findByUserIdAndProgramAndWalletStatusOrderById(
        userId: String,
        program: Int,
        walletStatus: WalletStatus
    ): Flux<Wallet>

    fun findByUserIdAndWalletStatusOrderById(
        userId: String,
        walletStatus: WalletStatus
    ): Flux<Wallet>

    fun findWalletByExpiryDateBefore(
        expiryDate: LocalDateTime
    ): Flux<Wallet>

    fun findAllByUserId(
        userId: String
    ): Flux<Wallet>

}