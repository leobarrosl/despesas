package com.leonardo.despesas.models.DTOs.requests;

import com.leonardo.despesas.models.Enums.CategoriasEnum;
import com.leonardo.despesas.models.Enums.TipoPagamentoEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LancamentoRequestDTO {

    private String descricao;
    private TipoPagamentoEnum tipoPagamento;
    private Integer numeroParcelas;
    private Boolean receita;
    private Double valorTotal;
    private CategoriasEnum categoria;
    private Integer perfilId;

    private LocalDate dataPagamento;
}
