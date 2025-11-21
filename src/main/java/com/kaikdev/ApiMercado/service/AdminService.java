package com.kaikdev.ApiMercado.service;

import com.kaikdev.ApiMercado.exception.ConflictException;
import com.kaikdev.ApiMercado.exception.ResourceNotFoundException;
import com.kaikdev.ApiMercado.model.Dto.FornecedorDto;
import com.kaikdev.ApiMercado.model.Dto.ProdutoDto;
import com.kaikdev.ApiMercado.model.Entity.Fornecedor;
import com.kaikdev.ApiMercado.model.Entity.Product;
import com.kaikdev.ApiMercado.model.Enum.ReputacaoFornecedor;
import com.kaikdev.ApiMercado.repository.FornecedorRepository;
import com.kaikdev.ApiMercado.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j // quando for adc log
public class AdminService {

    private final ProductRepository productRepository;
    private final FornecedorRepository fornecedorRepository;
    private final EmailService emailService;

    @Value("${app.admin.email}")
    private String adminEmail;

    public void ativar(Long id){
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", id));
        fornecedor.setAtivo(true);
        fornecedorRepository.save(fornecedor);
    }

    public void desativar(Long id){
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", id));
        fornecedor.setAtivo(false);
        fornecedorRepository.save(fornecedor);
    }

    public Fornecedor postarFornecedor(FornecedorDto dto){
        if (fornecedorRepository.existsByCnpj(dto.getCnpj())) {
            throw new ConflictException("Fornecedor já existe para o CNPJ " + dto.getCnpj());
        }

        ReputacaoFornecedor reputacao = dto.getReputacao() != null
                ? ReputacaoFornecedor.valueOf(dto.getReputacao().toUpperCase(Locale.ROOT))
                : ReputacaoFornecedor.BOM;

        var fornecedor = Fornecedor.builder()
                .name(dto.getName())
                .cnpj(dto.getCnpj())
                .ativo(dto.isAtivo())
                .reputacaoFornecedor(reputacao)
                .build();

        return fornecedorRepository.save(fornecedor);
    }

    public Product postarProduto(ProdutoDto produtoDto){
        if(productRepository.existsByName(produtoDto.getName())){
            throw new ConflictException("Produto já existe com o nome " + produtoDto.getName());
        }

        var product = Product.builder()
                .name(produtoDto.getName())
                .quantidadeAtual(produtoDto.getQuantidadeAtual())
                .capacidadeMaxima(produtoDto.getCapacidadeMaxima())
                .unidade(produtoDto.getUnidade())
                .categoria(produtoDto.getCategoria())
                .build();

        return productRepository.save(product);
    }

    public Product atualizarEstoque(Long id, Integer novaQuantidade){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", id));

        product.setQuantidadeAtual(novaQuantidade);
        Product atualizado = productRepository.save(product);

        verificarEstoqueCheio(atualizado);

        return atualizado;
    }

    private void verificarEstoqueCheio(Product product){
        Integer quantidadeAtual = product.getQuantidadeAtual();
        Integer capacidadeMaxima = product.getCapacidadeMaxima();

        if(quantidadeAtual == null || capacidadeMaxima == null){
            log.warn("Produto {} sem configuração de estoque completa. QuantidadeAtual={} CapacidadeMaxima={}",
                    product.getId(), quantidadeAtual, capacidadeMaxima);
            return;
        }


        if(quantidadeAtual >= capacidadeMaxima){
            String assunto = "Estoque cheio - " + product.getName();
            String html = montarMensagemEstoqueCheio(product);

            emailService.enviarEmailHtml(adminEmail, assunto, html);
        }
    }

    private String montarMensagemEstoqueCheio(Product product){
        return """
        <html>
            <body style="font-family: Arial, sans-serif; background:#f4f4f4; padding:20px;">
                <div style="max-width:520px; margin:auto; background:white; padding:25px; border-radius:12px; box-shadow:0 4px 16px rgba(0,0,0,0.12);">
                    <h2 style="color:#4F46E5; text-align:center; margin-top:0;">
                        📦 Estoque Cheio
                    </h2>

                    <p style="font-size:16px; color:#333;">
                        O estoque do produto <strong>%s</strong> atingiu sua capacidade máxima.
                    </p>
                    <div style="background:#EEF2FF; padding:15px; border-radius:8px; margin:20px 0; border-left:5px solid #4F46E5;">
                        <p style="margin:0; font-size:15px; color:#111;">
                            <strong>Quantidade atual:</strong> %d<br>
                            <strong>Capacidade máxima:</strong> %d
                        </p>
                    </div>

                    <p style="font-size:15px; color:#444;">
                        Recomenda-se verificar o armazenamento e considerar ajustes no espaço disponível.
                    </p>

                    <p style="font-size:12px; color:#888; text-align:center; margin-top:30px;">
                        Sistema ApiMercado • Notificação Automática
                    </p>

                </div>
            </body>
        </html>
        """.formatted(
                product.getName(),
                product.getQuantidadeAtual(),
                product.getCapacidadeMaxima()
        );
    }
}
