package com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long categoriaId;
    private String categoriaNombre;
}
