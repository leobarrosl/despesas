package com.leonardo.despesas.models.DTOs.responses;

import com.leonardo.despesas.models.Enums.CategoriasEnum;
import com.leonardo.despesas.models.Enums.TipoPagamentoEnum;
import com.leonardo.despesas.models.entities.Lancamento;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LancamentoResponseDTO {

    private Integer id;
    private String descricao;
    private TipoPagamentoEnum tipoPagamento;
    private Integer numeroParcelas;
    private boolean receita;
    private Double valorTotal;
    private CategoriasEnum categoria;

    public static LancamentoResponseDTO toDTO(Lancamento entity) {
        return new LancamentoResponseDTO(
                entity.getId(),
                entity.getDescricao(),
                entity.getTipoPagamento(),
                entity.getNumeroParcelas(),
                entity.isReceita(),
                entity.getValorTotal(),
                entity.getCategoria()
        );
    }
}
