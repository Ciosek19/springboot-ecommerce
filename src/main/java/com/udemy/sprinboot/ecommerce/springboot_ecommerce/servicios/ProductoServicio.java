package com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.excepciones.APIException;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.excepciones.RecursoNoEncotradoException;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.ProductoDTO;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Categoria;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Producto;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta.ProductoRespuesta;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.repositorios.interfaces.ICategoriaRepositorio;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.repositorios.interfaces.IProductoRepositorio;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces.IArchivoServicio;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces.IProductoServicio;

@Service
public class ProductoServicio implements IProductoServicio {

    @Autowired
    private ICategoriaRepositorio categoriaRepositorio;

    @Autowired
    private IProductoRepositorio productoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IArchivoServicio archivoServicio;

    @Value("${projecto.imagenes}")
    private String ruta;

    @Override
    public ProductoDTO agregarProducto(Long categoriaId, ProductoDTO productoDTO) {
        Categoria categoria = categoriaRepositorio.findById(categoriaId)
                .orElseThrow(() -> new RecursoNoEncotradoException("Categoria", "categoriaId", categoriaId));

        List<Producto> productos = categoria.getProductos();
        if (productos.stream().anyMatch(e -> e.getProductoNombre().equals(productoDTO.getProductoNombre()))) {
            throw new APIException("Ya existe ese producto");

        }

        Producto producto = modelMapper.map(productoDTO, Producto.class);

        producto.setPrecio(productoDTO.getPrecio());
        producto.setPrecio(productoDTO.getDescuento());
        producto.setImagen("default.png");
        producto.setCategoria(categoria);
        producto.setPrecioFinal(producto.calcularPrecioFinal(productoDTO.getPrecio(), productoDTO.getDescuento()));

        Producto productoGuardado = productoRepositorio.save(producto);
        return modelMapper.map(productoGuardado, ProductoDTO.class);
    }

    @Override
    public ProductoRespuesta obtenerProductos(Integer paginaNumero, Integer paginaTamano, String ordenarPor, String ordenarDireccion) {
        Sort ordenarPorYenOrden = ordenarDireccion.equalsIgnoreCase("asc")
        ? Sort.by(ordenarPor).ascending()
        : Sort.by(ordenarPor).descending();

        Pageable paginaDetalles = PageRequest.of(paginaNumero,paginaTamano,ordenarPorYenOrden);

        Page<Producto> paginaProductos = productoRepositorio.findAll(paginaDetalles);
        List<Producto> productos = paginaProductos.getContent();

        
        List<ProductoDTO> productosDTO = productos.stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .collect(Collectors.toList());

        ProductoRespuesta productoRespuesta = new ProductoRespuesta();
        productoRespuesta.setContenido(productosDTO);
        productoRespuesta.setNumPagina(paginaProductos.getNumber());
        productoRespuesta.setTamPagina(paginaProductos.getSize());
        productoRespuesta.setTotalElementos(paginaProductos.getTotalElements());
        productoRespuesta.setTotalPaginas(paginaProductos.getTotalPages());
        productoRespuesta.setEsUltimaPagina(paginaProductos.isLast());

        return productoRespuesta;
    }

    @Override
    public ProductoRespuesta buscarPorCategoria(Long categoriaId, Integer paginaNumero, Integer paginaTamano, String ordenarPor, String ordenarDireccion) {
        Categoria categoria = categoriaRepositorio.findById(categoriaId)
                .orElseThrow(() -> new RecursoNoEncotradoException("Categoria", "categoriaId", categoriaId));

        Sort ordenarPorYenOrden = ordenarDireccion.equalsIgnoreCase("asc")
        ? Sort.by(ordenarPor).ascending()
        : Sort.by(ordenarPor).descending();

        Pageable paginaDetalles = PageRequest.of(paginaNumero,paginaTamano,ordenarPorYenOrden);

        Page<Producto> paginaProductos = productoRepositorio.findByCategoriaOrderByPrecioAsc(categoria, paginaDetalles);

        List<Producto> productos = paginaProductos.getContent();

        List<ProductoDTO> productosDTO = productos.stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .collect(Collectors.toList());

        ProductoRespuesta productoRespuesta = new ProductoRespuesta();
        productoRespuesta.setContenido(productosDTO);
        productoRespuesta.setNumPagina(paginaProductos.getNumber());
        productoRespuesta.setTamPagina(paginaProductos.getSize());
        productoRespuesta.setTotalElementos(paginaProductos.getTotalElements());
        productoRespuesta.setTotalPaginas(paginaProductos.getTotalPages());
        productoRespuesta.setEsUltimaPagina(paginaProductos.isLast());
        return productoRespuesta;
    }

    @Override
    public ProductoRespuesta buscarPorPalabra(String palabra, Integer paginaNumero, Integer paginaTamano, String ordenarPor, String ordenarDireccion) {
        Sort ordenarPorYenOrden = ordenarDireccion.equalsIgnoreCase("asc")
        ? Sort.by(ordenarPor).ascending()
        : Sort.by(ordenarPor).descending();

        Pageable paginaDetalles = PageRequest.of(paginaNumero,paginaTamano,ordenarPorYenOrden);

        Page<Producto> paginaProductos = productoRepositorio.findByProductoNombreLikeIgnoreCase('%' + palabra + '%',paginaDetalles);

        List<Producto> productos = paginaProductos.getContent();

        if (productos.isEmpty())
            throw new APIException("No existen productos que contengan " + palabra);

        List<ProductoDTO> productoDTOs = productos.stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .toList();

        ProductoRespuesta productoRespuesta = new ProductoRespuesta();
        productoRespuesta.setContenido(productoDTOs);
        productoRespuesta.setNumPagina(paginaProductos.getNumber());
        productoRespuesta.setTamPagina(paginaProductos.getSize());
        productoRespuesta.setTotalElementos(paginaProductos.getTotalElements());
        productoRespuesta.setTotalPaginas(paginaProductos.getTotalPages());
        productoRespuesta.setEsUltimaPagina(paginaProductos.isLast());
        return productoRespuesta;
    }

    @Override
    public ProductoDTO actualizarProducto(Long productoId, ProductoDTO productoDto) {
        // Obtener el producto existente
        Producto productoDeBd = productoRepositorio.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncotradoException("Producto", "productoId", productoId));

        Producto producto = modelMapper.map(productoDeBd, Producto.class);

        productoDeBd.setProductoNombre(producto.getProductoNombre());
        productoDeBd.setDescripcion(producto.getDescripcion());
        productoDeBd.setCantidad(producto.getCantidad());
        productoDeBd.setDescuento(producto.getDescuento());
        productoDeBd.setPrecio(producto.getPrecioFinal());

        Producto productoGuardado = productoRepositorio.save(productoDeBd);

        return modelMapper.map(productoGuardado, ProductoDTO.class);
    }

    @Override
    public ProductoDTO eliminarProducto(Long productoId) {
        // Obtener el producto existente
        Producto productoDeBd = productoRepositorio.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncotradoException("Producto", "productoId", productoId));

        productoRepositorio.delete(productoDeBd);

        return modelMapper.map(productoDeBd, ProductoDTO.class);
    }

    @Override
    public ProductoDTO actualizarImagenProducto(Long productoId, MultipartFile imagen) throws IOException {
        // Obtener el producto existente
        Producto productoDeBd = productoRepositorio.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncotradoException("Producto", "productoId", productoId));

        // Subir imagen al servidor
        // Obtener nombre de la imagen subida
        String nombreArchivo = archivoServicio.subirImagen(ruta, imagen);

        // Actualizar producto con el nombre nuevo
        productoDeBd.setImagen(nombreArchivo);

        // Guardar producto en BD
        Producto productoGuardado = productoRepositorio.save(productoDeBd);

        // Devolver producto DTO
        return modelMapper.map(productoGuardado, ProductoDTO.class);
    }

}
