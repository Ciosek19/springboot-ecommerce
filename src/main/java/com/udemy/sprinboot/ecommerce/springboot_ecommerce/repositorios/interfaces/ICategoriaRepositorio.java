package com.udemy.sprinboot.ecommerce.springboot_ecommerce.repositorios.interfaces;

import com.udemy.sprinboot.ecommerce.springboot_ecommerce.modelos.Entidades.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepositorio extends JpaRepository<Categoria,Long> {
    Categoria findByCategoriaNombre(String nombre);
}
