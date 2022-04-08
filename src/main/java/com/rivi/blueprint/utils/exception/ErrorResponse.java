package com.rivi.blueprint.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Armaan (armaan6651)
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String error;
    private List<String> errorMsgs;
    private String path;
}
