package com.leonardo.despesas.models.DTOs.responses;

import com.leonardo.despesas.models.DTOs.requests.ParcelaChangeDTO;
import com.leonardo.despesas.models.entities.Parcela;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ParcelaResponseDTO {

    private Integer id;
    private LocalDate dataPagamento;
    private Double valor;
    private boolean pago;

    public static ParcelaResponseDTO toDTO(Parcela entity) {
        return new ParcelaResponseDTO(
                entity.getId(),
                entity.getDataPagamento(),
                entity.getValor(),
                entity.isPago()
        );
    }
}
