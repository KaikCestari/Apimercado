package com.kaikdev.ApiMercado.controller;

import com.kaikdev.ApiMercado.model.Dto.EntradaEstoqueRequest;
import com.kaikdev.ApiMercado.model.Dto.EstoqueProdutoResponse;
import com.kaikdev.ApiMercado.model.Dto.FornecedorRankingResponse;
import com.kaikdev.ApiMercado.model.Dto.RegistrarVendaRequest;
import com.kaikdev.ApiMercado.model.Dto.SaidaEstoqueRequest;
import com.kaikdev.ApiMercado.model.Dto.VendaResponse;
import com.kaikdev.ApiMercado.service.EstoqueService;
import com.kaikdev.ApiMercado.service.VendasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class GerenteController {

    private final EstoqueService estoqueService;
    private final VendasService vendasService;

    @PostMapping("/estoque/entrada")
    public ResponseEntity<EstoqueProdutoResponse> registrarEntrada(@Valid @RequestBody EntradaEstoqueRequest request) {
        return ResponseEntity.ok(estoqueService.registrarEntrada(request));
    }

    @PostMapping("/estoque/saida")
    public ResponseEntity<EstoqueProdutoResponse> registrarSaida(@Valid @RequestBody SaidaEstoqueRequest request) {
        return ResponseEntity.ok(estoqueService.registrarSaida(request));
    }

    @PostMapping("/vendas")
    public ResponseEntity<VendaResponse> registrarVenda(@Valid @RequestBody RegistrarVendaRequest request) {
        return ResponseEntity.ok(vendasService.registrarVenda(request));
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<VendaResponse>> listarVendas() {
        return ResponseEntity.ok(vendasService.listarVendas());
    }

    @GetMapping("/estoque")
    public ResponseEntity<List<EstoqueProdutoResponse>> listarEstoque() {
        return ResponseEntity.ok(estoqueService.listarEstoque());
    }

    @GetMapping("/fornecedores/ranking")
    public ResponseEntity<List<FornecedorRankingResponse>> rankingFornecedores() {
        return ResponseEntity.ok(vendasService.gerarRankingFornecedores());
    }
}
