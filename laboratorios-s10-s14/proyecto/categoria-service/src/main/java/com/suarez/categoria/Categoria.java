package com.suarez.categoria;
import jakarta.validation.constraints.NotBlank;
public record Categoria(Long id, @NotBlank String nombre, String estado) {}
