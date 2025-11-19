package com.kaikdev.ApiMercado.Service;

import com.kaikdev.ApiMercado.Model.Dto.FornecedorDto;
import com.kaikdev.ApiMercado.Model.Dto.ProdutoDto;
import com.kaikdev.ApiMercado.Model.Entity.Fornecedor;
import com.kaikdev.ApiMercado.Model.Entity.Product;
import com.kaikdev.ApiMercado.Model.Enum.ReputacaoFornecedor;
import com.kaikdev.ApiMercado.Repository.FornecedorRepository;
import com.kaikdev.ApiMercado.Repository.ProductRepository;
import com.kaikdev.ApiMercado.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminService {

 private final ProductRepository productRepository;
 private final FornecedorRepository fornecedorRepository;



 public void ativar(Long id){
     Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow();
     fornecedor.setAtivo(true);
     fornecedorRepository.save(fornecedor);
 }
 public void desativar(Long id){
     Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow();
     fornecedor.setAtivo(false);
     fornecedorRepository.save(fornecedor);
 }
 public Fornecedor postarFornecedor(FornecedorDto dto){
     if(fornecedorRepository.existsByCnpj(dto.getCnpj())){
         throw new RuntimeException("Fornecedor ja existe");
     }
      var fornecedor = Fornecedor.builder()
             .name(dto.getName())
             .cnpj(dto.getCnpj())
             .ativo(dto.isAtivo())
             .reputacaoFornecedor(ReputacaoFornecedor.valueOf(dto.getReputacao()))
             .build();
     return fornecedorRepository.save(fornecedor);
 }
 public Product postarProduto(ProdutoDto produtoDto){
var product = Product.builder()
        .name(produtoDto.getName())
        .createdAt(LocalDateTime.parse(produtoDto.getCreatedAt()))
        .estoque_atual(produtoDto.getEstoque_atual())
        .unidade(produtoDto.getUnidade())
        .categoria(produtoDto.getCategoria())
        .build();
return productRepository.save(product);
 }

}
