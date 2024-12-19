package com.leonardo.despesas.controllers;

import com.leonardo.despesas.models.DTOs.responses.ErrorResponse;
import com.leonardo.despesas.models.entities.Perfil;
import com.leonardo.despesas.models.entities.User;
import com.leonardo.despesas.models.repositories.PerfilRepository;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilRepository perfilRepository;

    @GetMapping
    public ResponseEntity<Object> perfil(@AuthenticationPrincipal User user) {

        if (perfilRepository.findByUser(user).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erro, perfil não encontrado"));
        }

        Perfil perfil = perfilRepository.findByUser(user).get();

        return ResponseEntity.ok(perfil);
    }

    @PutMapping
    public ResponseEntity<Object> atualizarPerfil(@AuthenticationPrincipal User user, @RequestBody Perfil perfilAtualizado) {

        if (perfilRepository.findByUser(user).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Perfil não encontrado"));
        }

        if (!perfilAtualizado.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Você não pode mudar o usuário vinculado ao perfil"));
        }

        Perfil perfil = perfilRepository.findByUser(user).get();

        if (perfilAtualizado.getDescricao().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("A descrição deve ser preenchida"));
        }

        perfil.update(perfilAtualizado);

        perfilRepository.save(perfil);

        return ResponseEntity.ok(perfil);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Perfil perfil) {

        if (perfil.getDescricao().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("A descrição deve ser preenchida"));
        }

        Perfil perfilSalvo = perfilRepository.save(perfil);

        return ResponseEntity.ok(perfilSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        Optional<Perfil> perfil = perfilRepository.findById(id);

        if(perfil.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Perfil não encontrado"));
        }

        perfilRepository.delete(perfil.get());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
