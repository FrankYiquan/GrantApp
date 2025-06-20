package com.brandeis.grant.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private int code;
    private T data;
    private LocalDateTime timestamp =  LocalDateTime.now();

    

}
