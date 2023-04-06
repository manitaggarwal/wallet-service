package com.manitaggarwal.walletservice.controller.request

import com.manitaggarwal.walletservice.domain.Program
import com.manitaggarwal.walletservice.domain.WalletType
import java.time.LocalDateTime

data class CreateProgramRequest(
    val name: String, val walletType: WalletType
)