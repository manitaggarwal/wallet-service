package com.manitaggarwal.walletservice.service

import com.manitaggarwal.walletservice.controller.request.CreateProgramRequest
import com.manitaggarwal.walletservice.dao.ProgramRepository
import com.manitaggarwal.walletservice.domain.Program
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProgramServiceImpl : ProgramService {

    @Autowired
    lateinit var programRepository: ProgramRepository
    override fun createProgram(createProgramRequest: CreateProgramRequest): Mono<Program> {
        return programRepository.save(
            Program(
                createProgramRequest.name,
                createProgramRequest.walletType
            )
        )
            .onErrorResume { exception ->
                Mono.error(exception)
            }
    }
}