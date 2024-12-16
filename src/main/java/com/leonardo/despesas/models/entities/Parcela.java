package com.leonardo.despesas.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "parcelas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter int id;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    private Double valor;

    private boolean pago;

    @ManyToOne
    @JoinColumn(name = "id_lancamento")
    private Lancamento lancamento;
}
