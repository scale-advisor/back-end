package org.scaleadvisor.backend.global.exception

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.scaleadvisor.backend.global.exception.dto.ErrorResponse
import org.scaleadvisor.backend.global.exception.model.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
class GlobalExceptionHandler {

    companion object {
        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
        private const val VALIDATION_LOG_MESSAGE         = "Validation error occurred: "
        private const val UNAUTHORIZED_LOG_MESSAGE      = "Unauthorized error occurred: "
        private const val FORBIDDEN_LOG_MESSAGE         = "Forbidden error occurred: "
        private const val NOT_FOUND_LOG_MESSAGE         = "Not found error occurred: "
        private const val CONFLICT_LOG_MESSAGE         = "Conflict error occurred: "
        private const val INTERNAL_SERVER_LOG_MESSAGE   = "Internal server error occurred: "
    }

    /** Validation Exception **/
    @ExceptionHandler(
        ValidationException::class,
        MissingServletRequestParameterException::class,
        MethodArgumentNotValidException::class,
        InvalidTokenException::class,
        KakaoBadRequestException::class,
        MessagingException::class
    )
    fun handleValidationExceptions(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.warn(VALIDATION_LOG_MESSAGE, ex)

        val customEx = when (ex) {
            is ValidationException ->
                ex
            else ->
                ValidationException(ex.message ?: "Validation failed")
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(customEx))
    }

    /** Unauthorized Exception **/
    @ExceptionHandler(
        UnauthorizedException::class,
    )
    fun handleUnauthorizedExceptions(ex: CustomException): ResponseEntity<ErrorResponse> {
        logger.warn(UNAUTHORIZED_LOG_MESSAGE, ex)
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(ex))
    }

    /** Forbidden Exception **/
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(ex: ForbiddenException): ResponseEntity<ErrorResponse> {
        logger.warn(FORBIDDEN_LOG_MESSAGE, ex)
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse(ex))
    }

    /** Not Found Exception **/
    @ExceptionHandler(NotFoundException::class, NoHandlerFoundException::class)
    fun handleNotFoundExceptions(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.warn(NOT_FOUND_LOG_MESSAGE, ex)

        val customEx = when (ex) {
            is NoHandlerFoundException ->
                NotFoundException(ex.message ?: "No handler found", BaseErrorCode.NOT_FOUND_EXCEPTION)
            is NotFoundException ->
                ex
            else ->
                NotFoundException(ex.message ?: "Resource not found")
        }

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(customEx))
    }

    /** Conflict Exception **/
    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(ex: ConflictException): ResponseEntity<ErrorResponse> {
        logger.warn(CONFLICT_LOG_MESSAGE, ex)
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(ex))
    }

    /** Fallback for all other exceptions **/
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error(INTERNAL_SERVER_LOG_MESSAGE, ex)
        val customEx = InternalServerException(ex.message ?: "Unexpected error")
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(customEx))
    }
}