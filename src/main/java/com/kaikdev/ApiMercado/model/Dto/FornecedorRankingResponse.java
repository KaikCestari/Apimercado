package com.kaikdev.ApiMercado.model.Dto;

import com.kaikdev.ApiMercado.model.Enum.ReputacaoFornecedor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FornecedorRankingResponse {
    Long id;
    String name;
    ReputacaoFornecedor reputacao;
    Double margemMedia;
    Integer entregasRealizadas;
    Double custoBeneficio;
    Double desempenhoHistorico;
    Double pontuacao;
}
