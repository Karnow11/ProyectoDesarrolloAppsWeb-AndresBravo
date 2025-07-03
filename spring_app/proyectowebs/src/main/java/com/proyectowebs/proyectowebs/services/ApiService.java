package com.proyectowebs.proyectowebs.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.proyectowebs.proyectowebs.models.Actividad;
import com.proyectowebs.proyectowebs.models.ActividadRepository;

import com.proyectowebs.proyectowebs.models.Actividad_tema;
import com.proyectowebs.proyectowebs.models.Actividad_temaRepository;

import com.proyectowebs.proyectowebs.models.Comuna;
import com.proyectowebs.proyectowebs.models.ComunaRepository;

@Service
public class ApiService {
    private final ActividadRepository actividadRepository;
    private final Actividad_temaRepository actividad_temaRepository;
    private final ComunaRepository comunaRepository;

    public ApiService(
        ActividadRepository actividadRepository,
        Actividad_temaRepository actividad_temaRepository,
        ComunaRepository comunaRepository) {
        this.actividadRepository = actividadRepository;
        this.actividad_temaRepository = actividad_temaRepository;
        this.comunaRepository = comunaRepository;
    }

    public List<Actividad> getActividades(int id) {
        List<Actividad> actividades = actividadRepository.findAll();
        List<Actividad> matchActividades = new ArrayList<Actividad>();
        for (Actividad act : actividades) {
            if (act.getId() == id) {
                matchActividades.add(act);
            }
        }
        return matchActividades;
    }

    public Optional<Comuna> getComunaById(int id) {
        List<Comuna> comunas = comunaRepository.findAll();  
        for (Comuna com : comunas) {
            if (com.getId() == id) {
                return Optional.of(com);
            }
        }
        return Optional.empty();
    }

    public Optional<String> getTemaById(int id) {
        List<Actividad_tema> temas = actividad_temaRepository.findAll();
        for (Actividad_tema tema : temas) {
            if (tema.getActividad_id() == id) {
                if(tema.getTema() == "otro"){
                    return Optional.of(tema.getGlosa_otro());
                }
                return Optional.of(tema.getTema());
            }
        }
        return Optional.empty();
    }

    public List<Map<String, String>> getStatsData() {
        // Define the start and end date
        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = LocalDate.of(2025, 7, 4);

        // Define the random number generator
        Random rand = new Random();

        // Generate the random data
        List<Map<String, String>> randomData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Map<String, String> data = new HashMap<>();
            data.put("date", getRandomDate(startDate, endDate, rand).toString());
            data.put("count", String.valueOf(getRandomInt(1, 10, rand)));
            randomData.add(data);
        }

        // Sort the data by date
        Collections.sort(randomData, (map1, map2) -> map1.get("date").compareTo(map2.get("date")));

        return randomData;
    }


    public List<Actividad> getMapData() {
        List<Actividad> actividades = actividadRepository.findAll();
        List<Actividad> mapData = new ArrayList<>();

        for (Actividad act : actividades) {
            mapData.add(act);
        }
        return mapData;
    }


    private static LocalDate getRandomDate(LocalDate startDate, LocalDate endDate, Random rand) {
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = rand.nextInt((int) totalDays + 1);
        return startDate.plusDays(randomDays);
    }

    private static int getRandomInt(int startInt, int endInt, Random rand) {
        return rand.nextInt(endInt - startInt + 1) + startInt;
    }


    
}
