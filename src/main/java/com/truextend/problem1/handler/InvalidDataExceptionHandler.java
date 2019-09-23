package com.truextend.problem1.handler;

import com.truextend.problem1.module.common.ResultInfo;
import com.truextend.problem1.module.common.ResultStatus;
import com.truextend.problem1.module.common.exception.BadRequestException;
import com.truextend.problem1.module.common.exception.ItemNotFoundException;
import com.truextend.problem1.module.common.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidDataExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResultInfo> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResultInfo(ex.getMessage()));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ResultInfo> handleItemNotFoundException(ItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildResultInfo(ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResultInfo> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildResultInfo(ex.getMessage()));
    }

    private ResultInfo buildResultInfo(String errorMessage) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(ResultStatus.ERROR);
        resultInfo.setMessage(errorMessage);
        return resultInfo;
    }
}
