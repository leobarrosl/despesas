package com.leonardo.despesas.models.entities;

import com.leonardo.despesas.models.Enums.CategoriasEnum;
import com.leonardo.despesas.models.Enums.TipoPagamentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "lancamentos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter int id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento")
    private TipoPagamentoEnum tipoPagamento;

    @Column(name = "numero_parcelas")
    private Integer numeroParcelas;

    private boolean receita;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriasEnum categoria;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    @OneToMany
    @JoinColumn(name = "id_lancamento")
    private List<Parcela> parcelas;

    public Lancamento(
            String descricao,
            TipoPagamentoEnum tipoPagamento,
            Integer numeroParcelas,
            Boolean receita,
            Double valorTotal,
            CategoriasEnum categoria,
            Perfil perfil
            )
    {
        this.descricao = descricao;
        this.tipoPagamento = tipoPagamento;
        this.numeroParcelas = numeroParcelas;
        this.receita = receita;
        this.valorTotal = valorTotal;
        this.categoria = categoria;
        this.perfil = perfil;

    }
}
