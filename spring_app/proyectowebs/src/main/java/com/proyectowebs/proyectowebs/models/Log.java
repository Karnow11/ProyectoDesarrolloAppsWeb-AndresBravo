package com.proyectowebs.proyectowebs.models;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "log")
public class Log{
    @Id
    @SequenceGenerator(
        name = "log_sequence",
        sequenceName = "log_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "log_sequence"
    )
    private Long id;

    @NotNull
    private LocalDateTime fecha;

    @NotNull
    private String mensaje;

    public Log(){}

    public Log(LocalDateTime fecha,
                  String mensaje){
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public static Boolean validateMensaje(String MensajeText) {
        // Placeholder para validación mas tarde
        return MensajeText != null && MensajeText.length() >= 5 && MensajeText.length() <= 200;
    }
}