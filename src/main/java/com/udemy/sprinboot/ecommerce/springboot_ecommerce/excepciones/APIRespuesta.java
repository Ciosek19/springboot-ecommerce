package com.udemy.sprinboot.ecommerce.springboot_ecommerce.excepciones;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIRespuesta {
    public String mensaje;
    private boolean estado;
}

