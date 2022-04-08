package com.rivi.blueprint.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private String status;
    private transient T data;
}
