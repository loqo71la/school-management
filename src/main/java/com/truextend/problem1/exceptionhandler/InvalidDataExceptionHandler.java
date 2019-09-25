package com.truextend.problem1.exceptionhandler;

import com.truextend.problem1.module.common.controller.ResultInfo;
import com.truextend.problem1.module.common.controller.ResultStatus;
import com.truextend.problem1.module.common.exception.BadRequestException;
import com.truextend.problem1.module.common.exception.ItemNotFoundException;
import com.truextend.problem1.module.common.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles exceptions related to invalid data.
 */
@ControllerAdvice
public class InvalidDataExceptionHandler {

    /**
     * Handler for bad request exception.
     *
     * @param ex bad request instance.
     * @return ResponseEntity with custom exception
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResultInfo> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResultInfo(ex.getMessage()));
    }

    /**
     * Handler for item not found exception.
     *
     * @param ex item not found instance.
     * @return ResponseEntity with custom exception.
     */
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ResultInfo> handleItemNotFoundException(ItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildResultInfo(ex.getMessage()));
    }

    /**
     * Handler for unauthorized exception.
     *
     * @param ex unauthorized instance.
     * @return ResponseEntity with custom exception.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResultInfo> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildResultInfo(ex.getMessage()));
    }

    /**
     * Builds a error result info.
     *
     * @param errorMessage error message.
     * @return a result info.
     */
    private ResultInfo buildResultInfo(String errorMessage) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(ResultStatus.ERROR);
        resultInfo.setMessage(errorMessage);
        return resultInfo;
    }
}
