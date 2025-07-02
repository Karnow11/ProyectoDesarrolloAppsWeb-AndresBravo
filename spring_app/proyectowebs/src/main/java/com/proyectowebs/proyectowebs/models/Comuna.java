package com.proyectowebs.proyectowebs.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "comuna")
public class Comuna{
    @Id
    @SequenceGenerator(
        name = "comuna_sequence",
        sequenceName = "comuna_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "comuna_sequence"
    )
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private Long region;

    public Comuna(){}

    public Comuna(String nombre,
                  Long region){
        this.nombre = nombre;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Long getRegion() {
        return region;
    }

    public static Boolean validateNombre(String nombreText) {
        // Placeholder para validación mas tarde
        return true;
    }
}