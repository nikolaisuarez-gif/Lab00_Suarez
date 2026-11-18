package com.suarez.producto;
import org.springframework.cloud.openfeign.FeignClient; import org.springframework.web.bind.annotation.*;
@FeignClient(name="categoria-service") interface CategoriaClient { @GetMapping("/api/categorias/{id}") CategoriaDTO obtener(@PathVariable long id); }
record CategoriaDTO(Long id,String nombre,String estado) {}
