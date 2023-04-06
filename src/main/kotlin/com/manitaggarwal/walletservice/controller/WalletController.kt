package com.manitaggarwal.walletservice.controller

import com.manitaggarwal.walletservice.controller.request.ConsumeWalletRequest
import com.manitaggarwal.walletservice.controller.request.CreateWalletRequest
import com.manitaggarwal.walletservice.dao.ProgramRepository
import com.manitaggarwal.walletservice.domain.Wallet
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
class WalletController {

    @Autowired
    lateinit var walletService: WalletService

    @Autowired
    lateinit var programRepository: ProgramRepository

    @PostMapping("/wallet", consumes = ["application/json"], produces = ["application/json"])
    fun createWallet(@RequestBody createWalletRequest: CreateWalletRequest): Mono<Wallet> {
        // get program from db and then create wallet
        return programRepository.findByName(createWalletRequest.program)
            .flatMap { program ->
                walletService.createWallet(createWalletRequest, program)
            }.switchIfEmpty(programNotFound())
    }

    @PostMapping("/consume", consumes = ["application/json"], produces = ["application/json"])
    fun consumeWallet(@RequestBody consumeWalletRequest: ConsumeWalletRequest): Flux<Wallet> {
        return programRepository.findByName(consumeWalletRequest.program)
            .flatMapMany { program ->
                walletService.consumeWallet(consumeWalletRequest, program)
            }.switchIfEmpty(programNotFound())
    }

    private fun programNotFound(): Mono<Wallet> =
        Mono.error(IllegalArgumentException("Program not found"))

}