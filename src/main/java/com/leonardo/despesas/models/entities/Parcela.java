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

    public Parcela(LocalDate dataPagamento, Double valor, boolean pago, Lancamento lancamento) {
        this.dataPagamento = dataPagamento;
        this.valor = valor;
        this.pago = pago;
        this.lancamento = lancamento;
    }

    public void update(LocalDate dataPagamento, Double valor, boolean pago) {
        this.dataPagamento = dataPagamento;
        this.valor = valor;
        this.pago = pago;
    }
}
