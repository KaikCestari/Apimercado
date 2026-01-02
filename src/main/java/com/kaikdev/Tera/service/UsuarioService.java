package com.kaikdev.Tera.service;

import com.kaikdev.Tera.exception.ResourceNotFoundException;
import com.kaikdev.Tera.model.Dto.CriarCategoriaRequest;
import com.kaikdev.Tera.model.Entity.Categoria;
import com.kaikdev.Tera.model.Entity.User;
import com.kaikdev.Tera.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService extends User {


private final CategoriaRepository categoriaRepository;


public Categoria criarCategoria(CriarCategoriaRequest criarCategoriaRequest){

    if (criarCategoriaRequest.name() == null) {
        throw new IllegalArgumentException("Nome null,por favor insira outro nome");
    }

    Categoria categoria = Categoria.builder()
            .cor(criarCategoriaRequest.cor())
            .name(criarCategoriaRequest.name())
            .build();
 return categoriaRepository.save(categoria);
}

public List<Categoria> Listarcategorias(){

List<Categoria> categorias = categoriaRepository.findAll();

    if (categorias.isEmpty()) {
        throw new ResourceNotFoundException("Nenhuma categoria encontrada");
    }

return categorias;
}


}
