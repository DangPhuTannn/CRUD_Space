package com.example.CRUDSpace.Exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.CRUDSpace.Model.DTO.Api.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRunTimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeninedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<Map<String, Object>> errorDetails = new ArrayList<>();
        ErrorCode finalErrorCode = ErrorCode.KEY_INVALID;

        for (FieldError error : fieldErrors) {
            String errorKey = error.getDefaultMessage();
            ErrorCode errorCode;
            try {
                errorCode = ErrorCode.valueOf(errorKey);
            } catch (IllegalArgumentException e) {
                errorCode = ErrorCode.KEY_INVALID;
            }

            if (finalErrorCode == ErrorCode.KEY_INVALID) {
                finalErrorCode = errorCode;
            }

            Map<String, Object> err = new HashMap<>();
            err.put("field", error.getField());
            err.put("errorCode", errorCode.name());
            err.put("message", errorCode.getMessage());
            errorDetails.add(err);
        }

        ApiResponse<Map<String, Object>> response = ApiResponse.<Map<String, Object>>builder()
                .code(finalErrorCode.getCode())
                .message(finalErrorCode.getMessage())
                .result(Map.of("errors", errorDetails))
                .build();

        return ResponseEntity.status(finalErrorCode.getStatusCode()).body(response);
    }

}
