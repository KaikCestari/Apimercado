package com.kaikdev.ApiMercado.service;

import com.kaikdev.ApiMercado.exception.BadRequestException;
import com.kaikdev.ApiMercado.exception.ResourceNotFoundException;
import com.kaikdev.ApiMercado.model.Entity.Fornecedor;
import com.kaikdev.ApiMercado.model.Entity.Product;
import com.kaikdev.ApiMercado.model.Enum.TipoMovimentoEstoque;
import com.kaikdev.ApiMercado.repository.EstoqueMovimentoRepository;
import com.kaikdev.ApiMercado.repository.FornecedorRepository;
import com.kaikdev.ApiMercado.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final ProductRepository productRepository;
    private final FornecedorRepository fornecedorRepository;
    private final EstoqueMovimentoRepository estoqueMovimentoRepository;

    @Transactional
    public EstoqueProdutoResponse registrarEntrada(EntradaEstoqueRequest request) {
        Product product = estoqueMovimentoRepository.findByProductId(request.getProductId()) .orElseThrow(() -> new ResourceNotFoundException("Produto not found", request.getProductId()));
        Fornecedor fornecedor = fornecedorRepository.findById(request.getFornecedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", request.getFornecedorId()));


       if(!estoqueMovimentoRepository.validarQuantidadeEstoque(request.getQuantidade())){
           throw new BadRequestException("Estoque insuficiente para a entrada solicitada");
       }
        if(!estoqueMovimentoRepository.validarValor(request.getPrecoCompra())){
            throw new RuntimeException("Valor abaixo de 0 " + request.getPrecoCompra());
        }

        int estoqueAtual = product.getQuantidadeAtual() == null ? 0 : product.getQuantidadeAtual();
        int novaQuantidade = estoqueAtual + request.getQuantidade();

        Integer capacidadeMaxima = product.getCapacidadeMaxima();
        if (capacidadeMaxima != null && novaQuantidade > capacidadeMaxima) {
            throw new BadRequestException("Quantidade excede a capacidade máxima do produto");
        }

        product.setQuantidadeAtual(novaQuantidade);
        productRepository.save(product);

        estoqueMovimentoRepository.save(EstoqueMovimento.builder()
                .product(product)
                .fornecedor(fornecedor)
                .quantidade(request.getQuantidade())
                .precoCompra(request.getPrecoCompra())
                .tipo(TipoMovimentoEstoque.ENTRADA)
                .build());

        int entregas = fornecedor.getEntregasRealizadas() == null ? 0 : fornecedor.getEntregasRealizadas();
        fornecedor.setEntregasRealizadas(entregas + 1);
        fornecedorRepository.save(fornecedor);

        return converter(product);
    }

    @Transactional
    public EstoqueProdutoResponse registrarSaida(SaidaEstoqueRequest request) {
        Product product = estoqueMovimentoRepository.findByProductId(request.getProductId()) .orElseThrow(() -> new ResourceNotFoundException("Produto not found", request.getProductId()));

        if(!estoqueMovimentoRepository.validarQuantidadeEstoque(request.getQuantidade())){
            throw new RuntimeException("Quantidade abaixo de 0" + request.getQuantidade());
        }

        int estoqueAtual = product.getQuantidadeAtual() == null ? 0 : product.getQuantidadeAtual();

        if (request.getQuantidade() > estoqueAtual) {
            throw new BadRequestException("Estoque insuficiente para a saída solicitada");
        }

        int novaQuantidade = estoqueAtual - request.getQuantidade();
        product.setQuantidadeAtual(novaQuantidade);
        productRepository.save(product);

        estoqueMovimentoRepository.save(EstoqueMovimento.builder()
                .product(product)
                .quantidade(request.getQuantidade())
                .tipo(TipoMovimentoEstoque.SAIDA)
                .motivo(request.getMotivo())
                .build());
        return converter(product);
    }

    @Transactional(readOnly = true)
    public List<EstoqueProdutoResponse> listarEstoque() {
        return productRepository.findAll().stream()
                .map(this::converter)
                .toList();
    }


    private EstoqueProdutoResponse converter(Product product) {
        return EstoqueProdutoResponse.builder()
                .productId(product.getId())
                .nome(product.getName())
                .categoria(product.getCategoria())
                .unidade(product.getUnidade())
                .quantidadeAtual(product.getQuantidadeAtual())
                .capacidadeMaxima(product.getCapacidadeMaxima())
                .build();
    }
}
