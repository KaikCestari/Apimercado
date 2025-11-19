package com.kaikdev.ApiMercado.Controller;

import com.kaikdev.ApiMercado.Model.Dto.FornecedorDto;
import com.kaikdev.ApiMercado.Model.Dto.ProdutoDto;
import com.kaikdev.ApiMercado.Model.Entity.Fornecedor;
import com.kaikdev.ApiMercado.Model.Entity.Product;
import com.kaikdev.ApiMercado.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/ativar/{id}")
    public void ativar(@PathVariable Long id){
        adminService.ativar(id);
    }
    @PutMapping("/desativar/{id}")
    public void desativar(@PathVariable Long id){
        adminService.desativar(id);
    }
    @PostMapping("/postarFornecedor")
    public ResponseEntity<Fornecedor> postarFornecedor(@RequestBody FornecedorDto dto){
        return ResponseEntity.ok(adminService.postarFornecedor(dto));
    }
    @PostMapping("/postarProduto")
    public ResponseEntity<Product> postarProduto(@RequestBody ProdutoDto dto){
        return ResponseEntity.ok(adminService.postarProduto(dto));
    }



}