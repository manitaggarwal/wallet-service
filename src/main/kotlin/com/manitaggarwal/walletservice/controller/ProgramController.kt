package com.manitaggarwal.walletservice.controller

import com.manitaggarwal.walletservice.controller.request.ConsumeWalletRequest
import com.manitaggarwal.walletservice.controller.request.CreateProgramRequest
import com.manitaggarwal.walletservice.controller.request.CreateWalletRequest
import com.manitaggarwal.walletservice.domain.Program
import com.manitaggarwal.walletservice.domain.Wallet
import com.manitaggarwal.walletservice.service.ProgramService
import com.manitaggarwal.walletservice.service.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1/api")
class ProgramController {

    @Autowired
    lateinit var programService: ProgramService

    @PostMapping("/program", consumes = ["application/json"], produces = ["application/json"])
    fun createWallet(@RequestBody createProgramRequest: CreateProgramRequest): Mono<Program> {
        return programService.createProgram(createProgramRequest)
    }

}