package com.robinbobin.test.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class PersonNotFoundException(message: String): ResponseStatusException(HttpStatus.NOT_FOUND, message)
class DuplicatePhoneNumberException(message: String): ResponseStatusException(HttpStatus.BAD_REQUEST, message)
class UnauthorizedException(message: String): ResponseStatusException(HttpStatus.UNAUTHORIZED, message)