package com.leonardo.despesas.controllers;

import com.leonardo.despesas.models.DTOs.responses.ErrorResponse;
import com.leonardo.despesas.models.entities.Parcela;
import com.leonardo.despesas.models.entities.Perfil;
import com.leonardo.despesas.models.repositories.ParcelasRepository;
import com.leonardo.despesas.models.repositories.PerfilRepository;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("parcelas")
@RequiredArgsConstructor
public class ParcelaController {

    private final ParcelasRepository parcelasRepository;
    private final PerfilRepository perfilRepository;

    // TODO terminar todos os métodos daqui

    @GetMapping("by-date/{perfilId}")
    public ResponseEntity<Object> findByPerfilPerMounth(@PathVariable Integer perfilId, @PathParam("month") Integer month, @PathParam("year") Integer year) {
        Optional<Perfil> perfil = perfilRepository.findById(perfilId);

        if (perfil.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Perfil não encontrado."));
        }

        List<Parcela> parcelas = parcelasRepository.findByPerfilAndMonth(perfil.get(), month, year);

        return ResponseEntity.ok(parcelas);
    }

    @PutMapping("pago-toggle/{parcelaId}")
    public ResponseEntity<Object> pagoToggle(@PathVariable Integer parcelaId){
        Optional<Parcela> parcela = parcelasRepository.findById(parcelaId);

        if (parcela.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Parcela não encontrada."));
        }

        parcela.get().setPago(!parcela.get().isPago());
        parcelasRepository.save(parcela.get());

        return ResponseEntity.ok(parcela);
    }
}
