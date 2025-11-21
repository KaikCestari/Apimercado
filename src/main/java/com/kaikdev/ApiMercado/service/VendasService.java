package com.kaikdev.ApiMercado.service;

import com.kaikdev.ApiMercado.exception.BadRequestException;
import com.kaikdev.ApiMercado.exception.ResourceNotFoundException;
import com.kaikdev.ApiMercado.model.Dto.FornecedorRankingResponse;
import com.kaikdev.ApiMercado.model.Dto.RegistrarVendaRequest;
import com.kaikdev.ApiMercado.model.Dto.VendaResponse;
import com.kaikdev.ApiMercado.model.Entity.Fornecedor;
import com.kaikdev.ApiMercado.model.Entity.Product;
import com.kaikdev.ApiMercado.model.Entity.Vendas;
import com.kaikdev.ApiMercado.repository.FornecedorRepository;
import com.kaikdev.ApiMercado.repository.ProductRepository;
import com.kaikdev.ApiMercado.repository.VendasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class
VendasService {

    private final VendasRepository vendasRepository;
    private final ProductRepository productRepository;
    private final FornecedorRepository fornecedorRepository;

    @Transactional
    public VendaResponse registrarVenda(RegistrarVendaRequest request) {
        validarValor(request.getPrecoCompra());
        validarValor(request.getPrecoVenda());
        validarQuantidade(request.getQuantidade());

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto", request.getProductId()));
        Fornecedor fornecedor = fornecedorRepository.findById(request.getFornecedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", request.getFornecedorId()));

        int estoqueAtual = product.getQuantidadeAtual() == null ? 0 : product.getQuantidadeAtual();
        if (request.getQuantidade() > estoqueAtual) {
            throw new BadRequestException("Estoque insuficiente para realizar a venda");
        }

        product.setQuantidadeAtual(estoqueAtual - request.getQuantidade());
        productRepository.save(product);

        double margem = calcularMargem(request.getPrecoVenda(), request.getPrecoCompra());

        Vendas venda = Vendas.builder()
                .product(product)
                .fornecedor(fornecedor)
                .quantidade(request.getQuantidade())
                .preco_compra(request.getPrecoCompra())
                .preco_venda(request.getPrecoVenda())
                .margem_lucro(margem)
                .build();

        Vendas persisted = vendasRepository.save(venda);

        atualizarHistoricoFornecedor(fornecedor, margem, request.getPrecoCompra(), request.getPrecoVenda());

        return mapear(persisted);
    }

    @Transactional(readOnly = true)
    public List<VendaResponse> listarVendas() {
        return vendasRepository.findAll().stream()
                .map(this::mapear)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FornecedorRankingResponse> gerarRankingFornecedores() {
        return fornecedorRepository.findAll().stream()
                .map(fornecedor -> {
                    double pontuacao = calcularPontuacaoRanking(fornecedor);
                    return FornecedorRankingResponse.builder()
                            .id(fornecedor.getId())
                            .name(fornecedor.getName())
                            .reputacao(fornecedor.getReputacaoFornecedor())
                            .margemMedia(padrao(fornecedor.getMargemMedia()))
                            .entregasRealizadas(padraoInt(fornecedor.getEntregasRealizadas()))
                            .custoBeneficio(padrao(fornecedor.getCustoBeneficio()))
                            .desempenhoHistorico(padrao(fornecedor.getDesempenhoHistorico()))
                            .pontuacao(pontuacao)
                            .build();
                })
                .sorted(Comparator.comparing(FornecedorRankingResponse::getPontuacao).reversed())
                .toList();
    }

    private void atualizarHistoricoFornecedor(Fornecedor fornecedor,
                                              double margem,
                                              double precoCompra,
                                              double precoVenda) {
        int totalAnterior = fornecedor.getTotalVendas() == null ? 0 : fornecedor.getTotalVendas();
        int novoTotal = totalAnterior + 1;

        double margemMediaAnterior = padrao(fornecedor.getMargemMedia());
        double novaMargemMedia = ((margemMediaAnterior * totalAnterior) + margem) / novoTotal;
        fornecedor.setMargemMedia(novaMargemMedia);

        double precoMedioCompraAnterior = padrao(fornecedor.getPrecoMedioCompra());
        double precoMedioVendaAnterior = padrao(fornecedor.getPrecoMedioVenda());
        double novoPrecoMedioCompra = ((precoMedioCompraAnterior * totalAnterior) + precoCompra) / novoTotal;
        double novoPrecoMedioVenda = ((precoMedioVendaAnterior * totalAnterior) + precoVenda) / novoTotal;
        fornecedor.setPrecoMedioCompra(novoPrecoMedioCompra);
        fornecedor.setPrecoMedioVenda(novoPrecoMedioVenda);

        double custoBeneficio = novoPrecoMedioVenda == 0
                ? 0
                : ((novoPrecoMedioVenda - novoPrecoMedioCompra) / novoPrecoMedioVenda) * 100;
        fornecedor.setCustoBeneficio(custoBeneficio);

        fornecedor.setTotalVendas(novoTotal);

        double desempenho = (novaMargemMedia * 0.6)
                + (custoBeneficio * 0.3)
                + (normalizarEntregas(fornecedor.getEntregasRealizadas()) * 0.1);
        fornecedor.setDesempenhoHistorico(desempenho);

        fornecedorRepository.save(fornecedor);
    }

    private double calcularMargem(double precoVenda, double precoCompra) {
        if (precoVenda <= 0) {
            return 0;
        }
        return (precoVenda - precoCompra) / precoVenda * 100;
    }

    private double calcularPontuacaoRanking(Fornecedor fornecedor) {
        double margem = padrao(fornecedor.getMargemMedia());
        double entregas = normalizarEntregas(fornecedor.getEntregasRealizadas());
        double custoBeneficio = padrao(fornecedor.getCustoBeneficio());
        double desempenho = padrao(fornecedor.getDesempenhoHistorico());

        return (margem * 0.4) + (entregas * 0.2) + (custoBeneficio * 0.2) + (desempenho * 0.2);
    }

    private double padrao(Double valor) {
        return valor == null ? 0 : valor;
    }

    private int padraoInt(Integer valor) {
        return valor == null ? 0 : valor;
    }

    private double normalizarEntregas(Integer entregasRealizadas) {
        int entregas = entregasRealizadas == null ? 0 : entregasRealizadas;
        int limite = 20;
        double ratio = Math.min(entregas, limite) / (double) limite;
        return ratio * 100;
    }

    private VendaResponse mapear(Vendas venda) {
        return VendaResponse.builder()
                .id(venda.getId())
                .produto(venda.getProduct() != null ? venda.getProduct().getName() : null)
                .fornecedor(venda.getFornecedor() != null ? venda.getFornecedor().getName() : null)
                .quantidade(venda.getQuantidade())
                .precoVenda(venda.getPreco_venda())
                .precoCompra(venda.getPreco_compra())
                .margemLucro(venda.getMargem_lucro())
                .createdAt(venda.getCreatedAt())
                .build();
    }

    private void validarQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new BadRequestException("Quantidade deve ser maior que zero");
        }
    }

    private void validarValor(Double valor) {
        if (valor == null || valor <= 0) {
            throw new BadRequestException("Valor deve ser maior que zero");
        }
    }
}
