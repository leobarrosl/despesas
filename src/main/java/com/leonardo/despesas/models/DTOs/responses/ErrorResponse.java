package com.leonardo.despesas.models.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ErrorResponse {
    private final @Getter String message;
}
