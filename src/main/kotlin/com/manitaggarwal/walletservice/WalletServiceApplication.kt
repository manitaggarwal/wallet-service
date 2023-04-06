package com.manitaggarwal.walletservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WalletServiceApplication

fun main(args: Array<String>) {
	runApplication<WalletServiceApplication>(*args)
}
