package com.proyectowebs.proyectowebs.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "nota")
public class Nota_actividad {

    @Id
    @SequenceGenerator(
        name = "nota_actividad_sequence",
        sequenceName = "nota_actividad_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "nota_actividad_sequence"
    )
    private int id;

    @NotNull
    private int actividad_id;

    @NotNull
    private int nota;

    // Getters and Setters
    public Nota_actividad(){}

    public Nota_actividad(int actividad_id, int nota){
        this.actividad_id = actividad_id;
        this.nota = nota;
    }
    public int getId() {
        return id;
    }
    public int getActividad_id() {
        return actividad_id;
    }
    public int getNota() {
        return nota;
    }
    public void setActividad_id(Integer id){
        actividad_id = id;
    }
    public void setNota(Integer nota_post){
        nota = nota_post;
    }
}