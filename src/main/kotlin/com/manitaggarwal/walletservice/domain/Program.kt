package com.manitaggarwal.walletservice.domain

import org.springframework.data.annotation.Id

data class Program(val name: String, val walletType: WalletType) {
    @Id
    var id: Int = 0
}
