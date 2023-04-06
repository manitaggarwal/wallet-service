package com.manitaggarwal.walletservice.dao

import com.manitaggarwal.walletservice.domain.*
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface WalletSummaryRepository : R2dbcRepository<WalletSummary, Long> {

    fun findWalletSummaryByUserIdAndProgram(
        userId: String,
        program: Int
    ): Mono<WalletSummary>

    fun findByUserId(
        userId: String
    ): Flux<WalletSummary>

}