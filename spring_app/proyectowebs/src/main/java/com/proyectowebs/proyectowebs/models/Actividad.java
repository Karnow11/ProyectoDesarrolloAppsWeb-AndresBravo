package com.proyectowebs.proyectowebs.models;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "actividad")
public class Actividad{

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
    private int comuna;

    @NotNull
    @Size(max = 100)
    private String sector;

    @NotNull
    @Size(min = 3)
    @Size(max = 200)
    private String nombre;

    @NotNull
    @Size(min = 3)
    @Size(max = 100)
    private String mail;

    @NotNull
    @Size(min = 9)
    @Size(max = 15)
    private String celular;

    @NotNull
    private LocalDateTime dia_hora_inicio;

    @NotNull
    private LocalDateTime dia_hora_termino;

    @NotNull
    @Size(max = 500)
    private String descripcion;

    @Nullable
    private String img_file; 

    public Actividad(){}

    public Actividad(int comuna,
                    String sector,
                    String nombre,
                    String mail,
                    String celular,
                    LocalDateTime dia_hora_inicio,
                    LocalDateTime dia_hora_termino,
                    String descripcion,
                    String img_filename){
        this.comuna = comuna;
        this.sector = sector;
        this.nombre = nombre;
        this.mail = mail;   
        this.celular = celular;
        this.dia_hora_inicio = dia_hora_inicio;
        this.dia_hora_termino = dia_hora_termino;
        this.descripcion = descripcion;
        this.img_file = img_filename;

    }
    public Long getId() {
        return id;
    }

    public int getComuna() {
        return comuna;
    }

    public String getSector() {
        return sector;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMail() {
        return mail;
    }

    public String getCelular() {
        return celular;
    }
    public String getImg(){
        return "Por ahora nada"; // Placeholder para image filename
    }
    public LocalDateTime getDia_hora_inicio() {
        return dia_hora_inicio;
    }

    public LocalDateTime getDia_hora_termino() {
        return dia_hora_termino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static Boolean validateActividad(String actText) {
        // Placeholder para validación mas tarde
        return true;
    }
}
