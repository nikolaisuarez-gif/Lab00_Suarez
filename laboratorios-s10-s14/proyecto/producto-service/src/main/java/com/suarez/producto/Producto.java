package com.suarez.producto;
import jakarta.validation.constraints.*;
public record Producto(Long id,@NotBlank String nombre,@Positive double precio,@PositiveOrZero int stock,@NotNull Long categoriaId) {}
