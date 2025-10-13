package com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.CategoriaDTO;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta.CategoriaRespuesta;

public interface ICategoriaServicio {
    CategoriaRespuesta obtenerTodasCategorias(Integer numPagina, Integer tamPagina, String ordenarPor, String ordenarEn);
    CategoriaDTO crearCategoria(CategoriaDTO categoria);
    CategoriaDTO eliminarCategoria(Long categoriaId);
    CategoriaDTO actualizarCategoria(CategoriaDTO categoria, Long categoriaid);

}
