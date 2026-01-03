package com.kaikdev.Tera.model.Dto;

import com.kaikdev.Tera.model.Entity.Categoria;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record GastosRequest(BigDecimal valor, Categoria categoria) {
}
