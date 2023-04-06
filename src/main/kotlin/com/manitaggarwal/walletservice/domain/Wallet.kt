package com.manitaggarwal.walletservice.domain

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Wallet(
    val userId: String, val earned: Int,
    var spent: Int, var expired: Int,
    var walletStatus: WalletStatus, val program: Int,
    val expiryDate: LocalDateTime, val transactionId: String
) {
    @Id
    var id: Int = 0
}