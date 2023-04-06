package com.manitaggarwal.walletservice.service

import com.manitaggarwal.walletservice.controller.request.ConsumeWalletRequest
import com.manitaggarwal.walletservice.controller.request.CreateWalletRequest
import com.manitaggarwal.walletservice.controller.response.CreateWalletResponse
import com.manitaggarwal.walletservice.domain.Program
import com.manitaggarwal.walletservice.domain.Wallet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface WalletService {
    fun createWallet(createWalletRequest: CreateWalletRequest, program: Program): Mono<Wallet>
    fun consumeWallet(consumeWalletRequest: ConsumeWalletRequest, program: Program): Flux<Wallet>
}