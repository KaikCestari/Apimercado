package com.kaikdev.Tera.controller;

import com.kaikdev.Tera.model.Dto.CriarCategoriaRequest;
import com.kaikdev.Tera.model.Entity.Categoria;
import com.kaikdev.Tera.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tera-api")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/newCategoria")
    public ResponseEntity<Categoria> newCategoria(@RequestBody CriarCategoriaRequest criarCategoriaRequest){
         Categoria categoria = usuarioService.criarCategoria(criarCategoriaRequest);
        return ResponseEntity.ok(categoria);
    }
    @GetMapping("/listarCategoria")
    public List<Categoria> listarCategoria(){
        return usuarioService.Listarcategorias();
    }

}
