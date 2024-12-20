package com.leonardo.despesas.models.DTOs.requests;

import java.time.LocalDate;

public record ParcelaChangeDTO(LocalDate dataPagamento, Double valor, boolean pago) {
}
