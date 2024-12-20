package com.leonardo.despesas.controllers;

import com.leonardo.despesas.models.DTOs.requests.ParcelaChangeDTO;
import com.leonardo.despesas.models.DTOs.requests.ValueByLancamentoDTO;
import com.leonardo.despesas.models.DTOs.responses.ErrorResponse;
import com.leonardo.despesas.models.DTOs.responses.LancamentoResponseDTO;
import com.leonardo.despesas.models.DTOs.responses.ParcelaResponseDTO;
import com.leonardo.despesas.models.entities.Lancamento;
import com.leonardo.despesas.models.entities.Parcela;
import com.leonardo.despesas.models.entities.Perfil;
import com.leonardo.despesas.models.repositories.LancamentosRepository;
import com.leonardo.despesas.models.repositories.ParcelasRepository;
import com.leonardo.despesas.models.repositories.PerfilRepository;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("parcelas")
@RequiredArgsConstructor
public class ParcelaController {

    private final ParcelasRepository parcelasRepository;
    private final LancamentosRepository lancamentosRepository;
    private final PerfilRepository perfilRepository;

    @GetMapping("by-date/{perfilId}")
    public ResponseEntity<Object> findByPerfilPerMounth(@PathVariable Integer perfilId, @PathParam("month") Integer month, @PathParam("year") Integer year) {
        Optional<Perfil> perfil = perfilRepository.findById(perfilId);

        if (perfil.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Perfil não encontrado."));
        }

        List<Parcela> parcelas = parcelasRepository.findByPerfilAndMonth(perfil.get(), month, year);

        return ResponseEntity.ok(
                StreamSupport.stream(parcelas.spliterator(), false).map(ParcelaResponseDTO::toDTO).toList()
        );
    }

    @PatchMapping("pago-toggle/{parcelaId}")
    public ResponseEntity<Object> pagoToggle(@PathVariable Integer parcelaId) {
        Optional<Parcela> parcelaBuscada = parcelasRepository.findById(parcelaId);

        if (parcelaBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Parcela não encontrada."));
        }
        Parcela parcela = parcelaBuscada.get();

        parcela.setPago(!parcela.isPago());
        parcelasRepository.save(parcela);

        return ResponseEntity.ok(ParcelaResponseDTO.toDTO(parcela));
    }

    @PatchMapping("update-value-by-lancamento")
    public ResponseEntity<Object> updateByLancamento(@RequestBody ValueByLancamentoDTO alteracao) {

        Optional<Lancamento> lancamentoBuscado = lancamentosRepository.findById(alteracao.lancamentoId());

        if (lancamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Lancamento não encontrada."));
        }

        Lancamento lancamento = lancamentoBuscado.get();
        for (Parcela parcela : lancamento.getParcelas()) {
            if (!parcela.isPago()) {
                parcela.setValor(alteracao.novoValor());
                parcelasRepository.save(parcela);
            }
        }
        lancamento.setValorTotal(parcelasRepository.sumByLancamento(lancamento));
        lancamentosRepository.save(lancamento);

        return ResponseEntity.ok(LancamentoResponseDTO.toDTO(lancamento));
    }

    @PutMapping("{parcelaId}")
    public ResponseEntity<Object> editarParcela(@PathVariable Integer parcelaId, @RequestBody ParcelaChangeDTO parcelaRecebida) {

        Optional<Parcela> parcelaBuscada = parcelasRepository.findById(parcelaId);

        if (parcelaBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Parcela não encontrada."));
        }

        Parcela parcela = parcelaBuscada.get();
        parcela.update(parcelaRecebida.dataPagamento(), parcelaRecebida.valor(), parcelaRecebida.pago());
        parcelasRepository.save(parcela);

        return ResponseEntity.ok(ParcelaResponseDTO.toDTO(parcela));
    }
}
