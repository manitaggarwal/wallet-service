package com.manitaggarwal.walletservice.dao

import com.manitaggarwal.walletservice.domain.Program
import com.manitaggarwal.walletservice.domain.Wallet
import com.manitaggarwal.walletservice.domain.WalletStatus
import com.manitaggarwal.walletservice.domain.WalletType
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface ProgramRepository : R2dbcRepository<Program, Long> {

    fun findByName(name: String): Mono<Program>


}