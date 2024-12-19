package com.leonardo.despesas.controllers;

import com.leonardo.despesas.models.DTOs.requests.LancamentoRequestDTO;
import com.leonardo.despesas.models.DTOs.responses.ErrorResponse;
import com.leonardo.despesas.models.Enums.TipoPagamentoEnum;
import com.leonardo.despesas.models.entities.Lancamento;
import com.leonardo.despesas.models.entities.Parcela;
import com.leonardo.despesas.models.entities.Perfil;
import com.leonardo.despesas.models.repositories.LancamentosRepository;
import com.leonardo.despesas.models.repositories.ParcelasRepository;
import com.leonardo.despesas.models.repositories.PerfilRepository;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("lancamentos")
@RequiredArgsConstructor
@Log4j2
public class LancamentoController {

    private final LancamentosRepository lancamentosRepository;
    private final ParcelasRepository parcelasRepository;
    private final PerfilRepository perfilRepository;

    // TODO implementar DTO pra tudo

    @GetMapping("by-perfil/{perfilId}")
    public ResponseEntity<Object> findByPerfil(@PathVariable Integer perfilId) {
        Optional<Perfil> perfil = perfilRepository.findById(perfilId);

        if (perfil.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Perfil não encontrado."));
        }

        List<Lancamento> lancamentos = lancamentosRepository.findByPerfil(perfil.get());

        return ResponseEntity.ok(lancamentos);
    }

    @GetMapping("recorrentes/{perfilId}")
    public ResponseEntity<Object> recorrentes(@PathVariable Integer perfilId) {
        Optional<Perfil> perfil = perfilRepository.findById(perfilId);

        if (perfil.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Perfil não encontrado."));
        }

        List<Lancamento> lancamentos = lancamentosRepository.findByTipoPagamento(TipoPagamentoEnum.RECORRENTE);

        return ResponseEntity.ok(lancamentos);
    }

    @GetMapping("/{lancamentoId}")
    public ResponseEntity<Object> findById(@PathVariable Integer lancamentoId) {

        Optional<Lancamento> lancamento = lancamentosRepository.findById(lancamentoId);

        if (lancamento.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Lançamento não encontrado."));
        }

        return ResponseEntity.ok(lancamento.get());
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody LancamentoRequestDTO lancamento) {

        Optional<Perfil> perfil = perfilRepository.findById(lancamento.getPerfilId());

        if (perfil.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Perfil não encontrado."));
        }

        Lancamento lancamentoRecebido = new Lancamento(lancamento.getDescricao(), lancamento.getTipoPagamento(), lancamento.getNumeroParcelas(), lancamento.getReceita(), lancamento.getValorTotal(), lancamento.getCategoria(), perfil.get());

        Lancamento lancamentoSalvo = lancamentosRepository.save(lancamentoRecebido);

        if (lancamentoSalvo.getTipoPagamento().equals(TipoPagamentoEnum.PARCELADO)) {
            for (int i = 0; i < lancamentoSalvo.getNumeroParcelas(); i++) {
                Parcela parcela = new Parcela(lancamento.getDataPagamento().plusMonths(i), lancamentoSalvo.getValorTotal() / lancamentoSalvo.getNumeroParcelas(), true, lancamentoSalvo);
                parcelasRepository.save(parcela);
            }
        } else {
            log.info("Pagamento não parcelado lançado!");
            Parcela parcela = new Parcela(lancamento.getDataPagamento(), lancamentoSalvo.getValorTotal(), true, lancamentoSalvo);
            parcelasRepository.save(parcela);
        }

        return ResponseEntity.ok(lancamentoSalvo);
    }
}
