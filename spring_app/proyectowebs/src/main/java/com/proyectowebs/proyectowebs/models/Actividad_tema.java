package com.proyectowebs.proyectowebs.models;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "actividad_tema")
public class Actividad_tema{
    @Id
    @SequenceGenerator(
        name = "actividad_sequence",
        sequenceName = "actividad_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "actividad_sequence"
    )
    private Long id;

    @NotNull
    private String tema;

    @Nullable
    private String glosa_otro;

    @NotNull
    private Long actividad_id;

    public Actividad_tema(){}

    public Actividad_tema(String tema,
                          String glosa_otro,
                          Long actividad_id){
        this.tema = tema;
        this.glosa_otro = glosa_otro;
        this.actividad_id = actividad_id;
    }

    public Long getId() {
        return id;
    }

    public String getTema() {
        return tema;
    }

    public String getGlosa_otro() {
        return glosa_otro;
    }

    public Long getActividad_id() {
        return actividad_id;
    }

    public static Boolean validateTema(String temaText) {
        // Placeholder para validación mas tarde
        return true;
    }
}