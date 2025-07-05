package com.example.CRUDSpace.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 1 -> 99 là Space Error
    SPACE_NOT_FOUND(1, "Space not found", HttpStatus.NOT_FOUND),
    SPACE_ALREADY_HAS_MAX_CHILDREN(2, "Space already has max children", HttpStatus.BAD_REQUEST),
    NOT_FOR_INVENTORY(3, "Inventory space is not be used in this function", HttpStatus.BAD_REQUEST),
    ONLY_FOR_INVENTORY(4, "This function is only for inventory space", HttpStatus.BAD_REQUEST),
    SITE_ID_NOT_FOUND(5, "Site ID not found", HttpStatus.NOT_FOUND),
    ONLY_FOR_BLOCK(6, "This function is only for Block type", HttpStatus.BAD_REQUEST),

    
    // 100 -> 199 là Type Error
    TYPE_NOT_FOUND(100, "Type not found", HttpStatus.NOT_FOUND),
    TYPE_LEVEL(101, "This level is already exist", HttpStatus.BAD_REQUEST),
    TYPE_NAME(102, "This name is already exist", HttpStatus.BAD_REQUEST),

    // 200 -> 299 là Equipment Error
    EQUIPMENT_NOT_FOUND(200, "Equipment not found", HttpStatus.NOT_FOUND),

    // 300 -> 399 là Value Error
    VALUE_NOT_FOUND(300, "Value not found", HttpStatus.NOT_FOUND),

    // 400 -> 499 là Material Error
    MATERIAL_NOT_FOUND(400, "Material not found", HttpStatus.NOT_FOUND),
    MATERIAL_ALREADY_EXIST(401, "Material already exist", HttpStatus.BAD_REQUEST),

    // 500 -> 599 là MaterialType Error
    MATERIAL_TYPE_NOT_FOUND(500, "Material Type not found", HttpStatus.NOT_FOUND),

    // 600 -> 699 là MaterialUnits Error
    MATERIAL_UNITS_NOT_FOUND(600, "Material Units not found", HttpStatus.NOT_FOUND),
    OVER_AVAILABLE_UNITS(601, "Over available units", HttpStatus.BAD_REQUEST),

    // 700 -> 799 là QEnergy Error
    QENERGY_NOT_FOUND(700, "QEnergy not found", HttpStatus.NOT_FOUND),
    NO_QENERGY_AT_SPECIFIC_DATE_RANGE(701, "This date range does not have data", HttpStatus.BAD_REQUEST),
    // 9000 -> 9999 là Validation
    FIELD_REQUIRED(9000, "This field is required", HttpStatus.BAD_REQUEST),
    FIELD_POSITIVE(9001, "This field must be positive", HttpStatus.BAD_REQUEST),
    KEY_INVALID(9999, "Invalid message key", HttpStatus.BAD_REQUEST),

    // 10000 -> 10999 là system error
    UNAUTHORIZED(10000, "You do not have permission", HttpStatus.FORBIDDEN),
    FAILED_TO_GET_ACCESS_TOKEN(10001, "Failed to get access token", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_TO_GET_LIVE_POWER(10002, "Failed to get live power", HttpStatus.INTERNAL_SERVER_ERROR);

    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
