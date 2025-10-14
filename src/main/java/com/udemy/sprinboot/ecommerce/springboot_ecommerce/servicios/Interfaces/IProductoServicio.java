package com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.ProductoDTO;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta.ProductoRespuesta;

public interface IProductoServicio {

    ProductoDTO agregarProducto(Long categoriaId, ProductoDTO productoDto);

    ProductoRespuesta obtenerProductos(Integer paginaNumero, Integer paginaTamano, String ordenarPor, String ordenarDireccion);

    ProductoRespuesta buscarPorCategoria(Long categoriaId, Integer paginaNumero, Integer paginaTamano, String ordenarPor, String ordenarDireccion);

    ProductoRespuesta buscarPorPalabra(String palabra, Integer paginaNumero, Integer paginaTamano, String ordenarPor, String ordenarDireccion);

    ProductoDTO actualizarProducto(Long productoId, ProductoDTO productoDto);

    ProductoDTO eliminarProducto(Long productoId);

    ProductoDTO actualizarImagenProducto(Long productoId, MultipartFile imagen) throws IOException;
}
