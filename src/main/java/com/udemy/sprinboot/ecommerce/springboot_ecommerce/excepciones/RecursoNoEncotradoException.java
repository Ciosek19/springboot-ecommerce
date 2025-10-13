package com.udemy.sprinboot.ecommerce.springboot_ecommerce.excepciones;

public class RecursoNoEncotradoException extends RuntimeException{
    private String recursoNombre;
    private String campo;
    private String campoNombre;
    private Long campoId;

    public RecursoNoEncotradoException() {
    }

    public RecursoNoEncotradoException(String recursoNombre, String campoNombre, String campo) {
        super(String.format("%s no econtrado con %s: %s", recursoNombre, campo, campoNombre));
        this.recursoNombre = recursoNombre;
        this.campoNombre = campoNombre;
        this.campo = campo;
    }

    public RecursoNoEncotradoException(String recursoNombre, String campo, Long campoId) {
        super(String.format("%s no econtrado con %s: %d", recursoNombre, campo, campoId));
        this.recursoNombre = recursoNombre;
        this.campo = campo;
        this.campoId = campoId;
    }
}
