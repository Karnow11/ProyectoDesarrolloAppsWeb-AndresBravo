package com.proyectowebs.proyectowebs.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.proyectowebs.proyectowebs.models.Actividad;
import com.proyectowebs.proyectowebs.services.ApiService;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ApiController {
    private final ApiService apiService;
    public ApiController(ApiService apiService) {
        this.apiService = apiService;

    }
    
    @GetMapping("/get-act/{id}")
    public Map<String, List<Actividad>> getActividadEndpoint(@PathVariable("id") int id) {
        List<Actividad> Actividades = apiService.getActividades(id);
        return Map.of("data", Actividades); // Encapsula la lista en un mapa con clave "data"
    }
    
    

    @GetMapping("/get-stats-data")
    public List<Map<String, String>> getStatsDataEndpoint() {
        return apiService.getStatsData();
    }

    @GetMapping("get-map-data")
    public List<Actividad> getMapDataEndpoint() {
        return apiService.getMapData();
    }
}
