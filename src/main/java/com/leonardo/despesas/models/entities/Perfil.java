package com.leonardo.despesas.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "perfis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter int id;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public void update(Perfil perfilAtualizado) {
        descricao = perfilAtualizado.getDescricao();
    }
}
