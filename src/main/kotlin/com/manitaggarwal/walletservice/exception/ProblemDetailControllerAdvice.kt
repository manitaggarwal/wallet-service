package com.manitaggarwal.walletservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ServerWebExchange

@ControllerAdvice
class ProblemDetailControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onIllegalArgumentException(
        exception: IllegalArgumentException, request: ServerWebExchange
    ): ProblemDetail {
        return ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(400),
            exception.message ?: "Bad Request"
        )
    }
}