package com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.CategoriaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRespuesta {
    private List<CategoriaDTO> contenido;
    private Integer numPagina;
    private Integer tamPagina;
    private Long totalElementos;
    private Integer totalPaginas;
    private boolean esUltimaPagina;
}
