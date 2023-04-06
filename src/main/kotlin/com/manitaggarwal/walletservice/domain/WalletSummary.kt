package com.manitaggarwal.walletservice.domain

import org.springframework.data.annotation.Id

data class WalletSummary(
    val userId: String, var balance: Int, val status: WalletStatus,
    val program: Int
) {
    @Id
    var id: Int = 0
}