package com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.excepciones.RecursoNoEncotradoException;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.ProductoDTO;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Categoria;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Producto;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta.ProductoRespuesta;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.repositorios.interfaces.ICategoriaRepositorio;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.repositorios.interfaces.IProductoRepositorio;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces.IProductoServicio;

@Service
public class ProductoServicio implements IProductoServicio {

    @Autowired
    private ICategoriaRepositorio categoriaRepositorio;

    @Autowired
    private IProductoRepositorio productoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductoDTO agregarProducto(Long categoriaId, Producto producto) {
        Categoria categoria = categoriaRepositorio.findById(categoriaId)
                .orElseThrow(() -> new RecursoNoEncotradoException("Categoria", "categoriaId", categoriaId));

        double especialPrecio = producto.getPrecio() - ((producto.getDescuento() * 0.01) * producto.getPrecio());

        producto.setImagen("default.png");
        producto.setCategoria(categoria);
        producto.setEspecialPrecio(especialPrecio);

        Producto productoGuardado = productoRepositorio.save(producto);
        return modelMapper.map(productoGuardado, ProductoDTO.class);
    }

    @Override
    public ProductoRespuesta obtenerProductos() {
        List<Producto> productos = productoRepositorio.findAll();
        List<ProductoDTO> productosDTO = productos.stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .collect(Collectors.toList());

        ProductoRespuesta productoRespuesta = new ProductoRespuesta();
        productoRespuesta.setContenido(productosDTO);
        
        return productoRespuesta;
    }

    @Override
    public ProductoRespuesta buscarPorCategoria(Long categoriaId) {
        Categoria categoria = categoriaRepositorio.findById(categoriaId)
               .orElseThrow(() -> new RecursoNoEncotradoException("Categoria", "categoriaId", categoriaId));

        List<Producto> productos = productoRepositorio.findByCategoriaOrderByPrecioAsc(categoria);
        List<ProductoDTO> productosDTO = productos.stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .collect(Collectors.toList());

        ProductoRespuesta productoRespuesta = new ProductoRespuesta();
        productoRespuesta.setContenido(productosDTO);
        
        return productoRespuesta;
    }

}
