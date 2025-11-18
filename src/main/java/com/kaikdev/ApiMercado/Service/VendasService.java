package com.kaikdev.ApiMercado.Service;

import com.kaikdev.ApiMercado.Repository.VendasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendasService {

    private final VendasRepository vendasRepository;
}
