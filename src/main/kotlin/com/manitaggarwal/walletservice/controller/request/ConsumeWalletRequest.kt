package com.manitaggarwal.walletservice.controller.request

import com.manitaggarwal.walletservice.domain.Program
import com.manitaggarwal.walletservice.domain.WalletType

data class ConsumeWalletRequest(
    val program: String,
    val userId: String, val amount: Int,
    val transactionId: String
)