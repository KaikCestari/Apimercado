package com.kaikdev.ApiMercado.service;

import com.kaikdev.ApiMercado.repository.VendasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendasService {

    private final VendasRepository vendasRepository;
}
