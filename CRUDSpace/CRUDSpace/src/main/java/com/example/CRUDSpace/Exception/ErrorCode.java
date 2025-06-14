package com.example.CRUDSpace.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

    KEY_INVALID(9999, "Invalid message key", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(403, "You do not have permission", HttpStatus.FORBIDDEN),
    // 1 -> 99 là Space Error
    SPACE_NOT_FOUND(1, "Space not found", HttpStatus.NOT_FOUND),

    // 100 -> 199 là Type Error
    TYPE_NOT_FOUND(100, "Type not found", HttpStatus.NOT_FOUND),
    TYPE_LEVEL(101, "This level is already exist", HttpStatus.BAD_REQUEST),
    TYPE_NAME(102, "This name is already exist", HttpStatus.BAD_REQUEST),

    // 200 -> 299 là Equipment Error
    EQUIPMENT_NOT_FOUND(200, "Equipment not found", HttpStatus.NOT_FOUND);

    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
