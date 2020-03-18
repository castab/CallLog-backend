package com.css.call.log.service

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleBadRequestException(e: BadRequestException) = ErrorResponse("${e.message}")

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleResourceNotFoundException(e: ResourceNotFoundException) = ErrorResponse("${e.message}")
}

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ErrorResponse(val message: String)

class BadRequestException(msg: String): RuntimeException(msg)
class ResourceNotFoundException(msg: String): RuntimeException(msg)