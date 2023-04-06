package com.manitaggarwal.walletservice.controller.request

import com.manitaggarwal.walletservice.domain.Program
import com.manitaggarwal.walletservice.domain.WalletType
import java.time.LocalDateTime

data class CreateWalletRequest(
    val program: String,
    val userId: String, val earned: Int,
    val spent: Int,
    val transactionId: String,
    val expiryDate: LocalDateTime
)