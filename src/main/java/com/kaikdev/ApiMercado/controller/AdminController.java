package com.kaikdev.ApiMercado.controller;

import com.kaikdev.ApiMercado.model.Dto.AtualizarEstoqueRequest;
import com.kaikdev.ApiMercado.model.Dto.FornecedorDto;
import com.kaikdev.ApiMercado.model.Dto.ProdutoDto;
import com.kaikdev.ApiMercado.model.Entity.Fornecedor;
import com.kaikdev.ApiMercado.model.Entity.Product;
import com.kaikdev.ApiMercado.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/ativar/{id}")
    public void ativar(@PathVariable @NotNull Long id){
        adminService.ativar(id);
    }
    @PutMapping("/desativar/{id}")
    public void desativar(@PathVariable @NotNull Long id){
        adminService.desativar(id);
    }
    @PostMapping("/postarFornecedor")
    public ResponseEntity<Fornecedor> postarFornecedor(@RequestBody @Valid FornecedorDto dto){
        return ResponseEntity.ok(adminService.postarFornecedor(dto));
    }
    @PostMapping("/postarProduto")
    public ResponseEntity<Product> postarProduto(@RequestBody @Valid ProdutoDto dto){
        return ResponseEntity.ok(adminService.postarProduto(dto));
    }

    @PutMapping("/produtos/{id}/estoque")
    public ResponseEntity<Product> atualizarEstoque(@PathVariable @NotNull Long id,
                                                    @RequestBody @Valid AtualizarEstoqueRequest request){
        return ResponseEntity.ok(adminService.atualizarEstoque(id, request.getQuantidadeAtual()));
    }



}
