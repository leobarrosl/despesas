package com.leonardo.despesas.models.repositories;

import com.leonardo.despesas.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
}