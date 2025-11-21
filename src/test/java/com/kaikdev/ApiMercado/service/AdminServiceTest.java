package com.kaikdev.ApiMercado.service;

import com.kaikdev.ApiMercado.exception.ConflictException;
import com.kaikdev.ApiMercado.exception.ResourceNotFoundException;
import com.kaikdev.ApiMercado.model.Dto.FornecedorDto;
import com.kaikdev.ApiMercado.model.Entity.Fornecedor;
import com.kaikdev.ApiMercado.model.Entity.Product;
import com.kaikdev.ApiMercado.repository.FornecedorRepository;
import com.kaikdev.ApiMercado.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private FornecedorRepository fornecedorRepository;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(adminService, "adminEmail", "admin@test.com");
    }

    @Test
    void postarFornecedor_deveLancarConflictQuandoCnpjJaExiste() {
        FornecedorDto dto = new FornecedorDto("123456", "Fornecedor", true, null, "BOM");
        when(fornecedorRepository.existsByCnpj(anyString())).thenReturn(true);

        assertThrows(ConflictException.class, () -> adminService.postarFornecedor(dto));
        verify(fornecedorRepository, never()).save(any());
    }

    @Test
    void postarFornecedor_devePersistirQuandoCnpjNovo() {
        FornecedorDto dto = new FornecedorDto("123456", "Fornecedor", true, null, "BOM");
        when(fornecedorRepository.existsByCnpj(anyString())).thenReturn(false);
        Fornecedor persisted = Fornecedor.builder().id(1L).name("Fornecedor").cnpj("123456").build();
        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(persisted);

        Fornecedor result = adminService.postarFornecedor(dto);

        assertThat(result).isEqualTo(persisted);
        ArgumentCaptor<Fornecedor> captor = ArgumentCaptor.forClass(Fornecedor.class);
        verify(fornecedorRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Fornecedor");
    }

    @Test
    void atualizarEstoque_deveLancarExcecaoQuandoProdutoInexistente() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> adminService.atualizarEstoque(1L, 10));
    }

    @Test
    void atualizarEstoque_deveEnviarEmailQuandoEstoqueCheio() {
        Product product = Product.builder()
                .id(1L)
                .name("Produto")
                .capacidadeMaxima(10)
                .quantidadeAtual(5)
                .build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        adminService.atualizarEstoque(1L, 10);

        verify(emailService).enviarEmailHtml(eq("admin@test.com"), contains("Estoque cheio"), contains("Produto"));
    }
}
