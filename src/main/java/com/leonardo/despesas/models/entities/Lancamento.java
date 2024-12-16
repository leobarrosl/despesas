package com.leonardo.despesas.models.entities;

import com.leonardo.despesas.models.Enums.CategoriasEnum;
import com.leonardo.despesas.models.Enums.TipoPagamentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriasEnum Categoria;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;
}
