package com.kaikdev.ApiMercado.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenPair {
    private String acessToken;
    private String refreshToken;
}
