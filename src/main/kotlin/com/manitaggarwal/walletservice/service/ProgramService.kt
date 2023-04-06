package com.manitaggarwal.walletservice.service

import com.manitaggarwal.walletservice.controller.request.ConsumeWalletRequest
import com.manitaggarwal.walletservice.controller.request.CreateProgramRequest
import com.manitaggarwal.walletservice.controller.request.CreateWalletRequest
import com.manitaggarwal.walletservice.controller.response.CreateWalletResponse
import com.manitaggarwal.walletservice.domain.Program
import com.manitaggarwal.walletservice.domain.Wallet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProgramService {
    fun createProgram(createProgramRequest: CreateProgramRequest): Mono<Program>
}