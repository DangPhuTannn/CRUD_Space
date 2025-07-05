package com.example.CRUDSpace.Model.DTO.Tuya;

import lombok.*;

@Data
public class TuyaResponse<T> {
    private int code;
    private boolean success;
    private String msg;
    private long t;
    private T result;
}