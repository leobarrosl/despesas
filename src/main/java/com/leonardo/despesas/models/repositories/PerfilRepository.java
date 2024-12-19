package com.leonardo.despesas.models.repositories;

import com.leonardo.despesas.models.entities.Perfil;
import com.leonardo.despesas.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    Optional<Perfil> findByUser(User user);
}
