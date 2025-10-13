package com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.excepciones.APIException;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.excepciones.RecursoNoEncotradoException;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.DTOs.CategoriaDTO;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Categoria;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Respuesta.CategoriaRespuesta;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.repositorios.interfaces.ICategoriaRepositorio;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.servicios.Interfaces.ICategoriaServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServicio implements ICategoriaServicio {

    private final ICategoriaRepositorio categoriaRepositorio;
    private final ModelMapper modelMapper;


    public CategoriaServicio(ICategoriaRepositorio categoriaRepositorio, ModelMapper modelMapper) {
        this.categoriaRepositorio = categoriaRepositorio;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoriaRespuesta obtenerTodasCategorias(Integer numPagina, Integer tamPagina, String ordenarPor, String ordenarEn) {
        Sort ordenarPorYenOrden = ordenarEn.equalsIgnoreCase("asc")
                ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();

        Pageable detallesPagina = PageRequest.of(numPagina,tamPagina, ordenarPorYenOrden);
        Page<Categoria> categoriaPagina = categoriaRepositorio.findAll(detallesPagina);
        List<Categoria> categorias = categoriaPagina.getContent();
        if (categorias.isEmpty())
            throw new APIException("No existe ninguna categoria");
        List<CategoriaDTO> categoriasDTOS = categorias.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .collect(Collectors.toList());

        CategoriaRespuesta categoriaRespuesta = new CategoriaRespuesta();
        categoriaRespuesta.setContenido(categoriasDTOS);

        categoriaRespuesta.setNumPagina(categoriaPagina.getNumber());
        categoriaRespuesta.setTamPagina(categoriaPagina.getSize());
        categoriaRespuesta.setTotalElementos(categoriaPagina.getTotalElements());
        categoriaRespuesta.setTotalPaginas(categoriaPagina.getTotalPages());
        categoriaRespuesta.setEsUltimaPagina(categoriaPagina.isLast());

        return categoriaRespuesta;
    }

    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);

        Categoria categoriaDeBd = categoriaRepositorio.findByCategoriaNombre(categoria.getCategoriaNombre());
        if (categoriaDeBd != null)
            throw new APIException("La categoria con el nombre "+categoriaDeBd.getCategoriaNombre()+" ya existe.");

        Categoria categoriaGuaradada = categoriaRepositorio.save(categoria);

        CategoriaDTO categoriaGuaradadaDTO = modelMapper.map(categoriaGuaradada, CategoriaDTO.class);
        return categoriaGuaradadaDTO;
    }

    @Override
    public CategoriaDTO eliminarCategoria(Long categoriaId) {
        Categoria categoriaGuardada = categoriaRepositorio.findById(categoriaId)
                .orElseThrow(() -> new RecursoNoEncotradoException("Categoria","Id",categoriaId));

        categoriaRepositorio.delete(categoriaGuardada);
        return modelMapper.map(categoriaGuardada, CategoriaDTO.class);
    }

    @Override
    public CategoriaDTO actualizarCategoria(CategoriaDTO categoriaDTO, Long categoriaId) {

        // 1. Buscamos la categoria en la BD por el ID
        Categoria categoriaDeBD = categoriaRepositorio.findById(categoriaId)
                .orElseThrow(() -> new RecursoNoEncotradoException("Categoria","Id",categoriaId));

        // 2. La categoriaDTO del param la mapeamos a Base
        Categoria categoriaMapeada = modelMapper.map(categoriaDTO, Categoria.class);

        // 3. Le insertamos el ID del parametro a la base mapeada
        categoriaMapeada.setCategoriaId(categoriaId);

        // 4. Guardamos en la BD
        Categoria categoriaGuardada = categoriaRepositorio.save(categoriaMapeada);

        // 5. Mapeamos a DTO y la retornamos
        return modelMapper.map(categoriaGuardada, CategoriaDTO.class);
    }


}
