package com.udemy.sprinboot.ecommerce.springboot_ecommerce.repositorios.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Categoria;
import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Producto;

@Repository
public interface IProductoRepositorio extends JpaRepository<Producto,Long>{
    Page<Producto> findByCategoriaOrderByPrecioAsc(Categoria categoria, Pageable paginaDetalles);

    Page<Producto> findByProductoNombreLikeIgnoreCase(String palabra, Pageable paginaDetalles);
}
