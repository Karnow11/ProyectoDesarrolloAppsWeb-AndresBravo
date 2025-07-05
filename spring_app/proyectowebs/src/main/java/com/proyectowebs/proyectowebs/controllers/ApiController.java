package com.proyectowebs.proyectowebs.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.proyectowebs.proyectowebs.models.Actividad;
import com.proyectowebs.proyectowebs.models.Nota_actividad;
import com.proyectowebs.proyectowebs.services.ApiService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ApiController {
    private final ApiService apiService;
    public ApiController(ApiService apiService) {
        this.apiService = apiService;

    }
    
    @GetMapping("/get-act/{id}")
    public Map<String, Optional<Actividad>> getActividadEndpoint(@PathVariable("id") int id) {
        Optional<Actividad> Actividades = apiService.getActividadById(id);
        return Map.of("data", Actividades); // Encapsula la lista en un mapa con clave "data"
    }
    
    

    @GetMapping("/get-stats-data")
    public List<Map<String, String>> getStatsDataEndpoint() {
        return apiService.getStatsData();
    }

    @GetMapping("/get-map-data")
    public List<Actividad> getMapDataEndpoint() {
        return apiService.getMapData();
    }

    @GetMapping("/get-vote-data/{id}")
    public List<Nota_actividad> getNotaDataEndpoint(@PathVariable("id") int id) {
        return apiService.getNotasById(id);
    }
}
