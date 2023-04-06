package com.manitaggarwal.walletservice.controller

import com.manitaggarwal.walletservice.domain.Wallet
import com.manitaggarwal.walletservice.domain.WalletSummary
import com.manitaggarwal.walletservice.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/v1/api")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/users/{userId}/wallets", produces = ["application/json"])
    fun getUserWallets(@PathVariable userId: String): Flux<Wallet> {
        // get all wallets for a user from db
        return userService.getUserWallets(userId)
    }

    @GetMapping("/users/{userId}/wallets-summaries", produces = ["application/json"])
    fun getUserWalletSummaries(@PathVariable userId: String): Flux<WalletSummary> {
        // get all wallet summaries for a user from db
        return userService.getUserWalletSummaries(userId)
    }

}