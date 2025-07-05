package com.proyectowebs.proyectowebs.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.proyectowebs.proyectowebs.models.Actividad;
import com.proyectowebs.proyectowebs.models.ActividadRepository;

import com.proyectowebs.proyectowebs.models.Comuna;
import com.proyectowebs.proyectowebs.models.ComunaRepository;

import com.proyectowebs.proyectowebs.models.Nota_actividad;
import com.proyectowebs.proyectowebs.models.Nota_actividadRepository;

@Service
public class AppService {

    private final String pathStatic;
    private final ActividadRepository actividadRepository;
    private final ComunaRepository comunaRepository;
    private final Nota_actividadRepository nota_actividadRepository;
    private final ApiService apiService;

    public AppService(ApiService apiService, ActividadRepository actividadRepository, 
    ComunaRepository comunaRepository, Nota_actividadRepository nota_actividadRepository) throws IOException {
        this.apiService = apiService;
        this.actividadRepository = actividadRepository;
        this.comunaRepository = comunaRepository;
        this.nota_actividadRepository = nota_actividadRepository;

        // Dynamically resolve the absolute path for the static directory
        Path staticDir = Paths.get(ResourceUtils.getFile("classpath:static").getAbsolutePath());
        this.pathStatic = staticDir.toString();
        System.out.println("Static path resolved to: " + this.pathStatic);
    }

    public List<Map<String, String>> getActividadesData(Integer pageSize) {
        List<Actividad> Actividades = actividadRepository.findAllByOrderByIdDesc(PageRequest.of(0, pageSize)).getContent();
        List<Map<String, String>> actividadesData = new ArrayList<>();
    
        for (Actividad act : Actividades) {
            Optional<Comuna> comunaOpt = apiService.getComunaById(act.getComuna());
            Optional<String> temaOpt = apiService.getTemaById(act.getId());
            Integer Nota = 0;
            Integer Notas = 0;
            List<Nota_actividad> notas = apiService.getNotasById(act.getId());
            for(Nota_actividad nota : notas){
                Nota += nota.getNota();
                Notas++;
            }
            if (Notas != 0){
                Nota = Nota/Notas;
            }
            System.out.println("Actividad ID: " + act.getId() + " | Suma notas: " + Nota + " | Cantidad notas: " + Notas);
            Map<String, String> actividadData = new HashMap<>();
            actividadData.put("id", "" + act.getId());
            actividadData.put("tema", temaOpt.orElse("Desconocido"));
            actividadData.put("comuna", comunaOpt.map(Comuna::getNombre).orElse("Desconocida"));
            actividadData.put("sector", act.getSector().toString());
            actividadData.put("nombre", act.getNombre().toString());
            actividadData.put("mail", act.getMail().toString());
            actividadData.put("celular", act.getCelular().toString());
            actividadData.put("inicio", act.getDia_hora_inicio().toString());
            actividadData.put("termino", act.getDia_hora_termino().toString());
            actividadData.put("descripcion", act.getDescripcion().toString());
            actividadData.put("image_filename", act.getImg());
            actividadData.put("nota","" + (Nota != 0 ? Nota : "-"));

            actividadesData.add(actividadData);
        }
        return actividadesData;
    }


    public List<Map<String, String>> getActividadesExpiradasData(Integer pageSize) {
        List<Actividad> Actividades = actividadRepository.findAllByOrderByIdDesc(PageRequest.of(0, pageSize)).getContent();
        List<Map<String, String>> actividadesData = new ArrayList<>();
        
        for (Actividad act : Actividades) {
            if(act.getDia_hora_inicio().isAfter(LocalDateTime.now())) {
                continue; // Skip expired activities
            }
            Optional<Comuna> comunaOpt = apiService.getComunaById(act.getComuna());
            Optional<String> temaOpt = apiService.getTemaById(act.getId());
            double Nota = 0.0;
            double Notas = 0.0;
            List<Nota_actividad> notas = apiService.getNotasById(act.getId());
            for(Nota_actividad nota : notas){
                Nota += nota.getNota();
                Notas++;
            }
            if (Notas != 0){
                Nota = Nota/Notas;
            }

            Map<String, String> actividadData = new HashMap<>();
            actividadData.put("id", "" + act.getId());
            actividadData.put("tema", temaOpt.orElse("Desconocido"));
            actividadData.put("comuna", comunaOpt.map(Comuna::getNombre).orElse("Desconocida"));
            actividadData.put("sector", act.getSector().toString());
            actividadData.put("nombre", act.getNombre().toString());
            actividadData.put("mail", act.getMail().toString());
            actividadData.put("celular", act.getCelular().toString());
            actividadData.put("inicio", act.getDia_hora_inicio().toString());
            actividadData.put("termino", act.getDia_hora_termino().toString());
            actividadData.put("descripcion", act.getDescripcion().toString());
            actividadData.put("image_filename", act.getImg());
            actividadData.put("nota", Nota != 0 ? String.format("%.2f", Nota) : "-");

            actividadesData.add(actividadData);
        }
        return actividadesData;
    }

    public List<Map<String, String>> getComunaData(Long id) {
        List<Comuna> Comunas = comunaRepository.findAll();
        List<Map<String, String>> ComunasData = new ArrayList<>();
        for (Comuna com : Comunas) {
            Map<String, String> comunaData = new HashMap<>();
            if (com.getId() == id){
                comunaData.put("id", com.getId().toString());
                comunaData.put("nombre", com.getNombre());
                comunaData.put("region", com.getRegion().toString());
                ComunasData.add(comunaData);
            }
        }
        return ComunasData;
    }

    public List<Map<String, String>> getNotaData(Long id) {
        List<Nota_actividad> Notas = nota_actividadRepository.findAll();
        List<Map<String, String>> NotasData = new ArrayList<>();
        for (Nota_actividad nota : Notas) {
            Map<String, String> notaData = new HashMap<>();
            if (nota.getId() == id){
                notaData.put("nota", "" + nota.getNota());
                NotasData.add(notaData);
            }
        }
        return NotasData;
    }

    public void handlePostNotaRequest(int id, int nota) throws Exception {
        Optional<Actividad> actividadOpt = apiService.getActividadById(id);
        if (actividadOpt.isPresent()) {
            Actividad actividad = actividadOpt.get();
            Nota_actividad notaActividad = new Nota_actividad();
            notaActividad.setActividad_id(actividad.getId());
            notaActividad.setNota(nota);
            nota_actividadRepository.save(notaActividad);
            System.out.println("Nota saved successfully for Actividad ID: " + id + " with nota: " + nota);
        } else {
            throw new IllegalArgumentException("Actividad with ID " + id + " does not exist.");
        }
    }

    public void handlePostRequest(
        int comuna,
        String sector,
        String nombre,
        String mail,
        String celular,
        LocalDateTime dia_hora_inicio,
        LocalDateTime dia_hora_termino,
        String descripcion,
        MultipartFile confImg) throws Exception {


        if (Actividad.validateActividad(descripcion)) {
            String _originalFilename = confImg.getOriginalFilename();
            if (_originalFilename == null || _originalFilename.isEmpty()) {
                throw new IllegalArgumentException("File name is empty.");
            }

            // Generate unique filename
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(_originalFilename.getBytes("UTF-8"));
            byte[] hash = md.digest();
            String _filename;
            try (Formatter formatter = new Formatter()) {
                for (byte b : hash) {
                    formatter.format("%02x", b);
                }
                _filename = formatter.toString();
            }

            String _extension = _originalFilename.substring(_originalFilename.lastIndexOf('.') + 1).toLowerCase();
            if (!_extension.matches("jpg|jpeg|png|gif")) {
                throw new IllegalArgumentException("Invalid file extension: " + _extension);
            }

            String imgFilename = _filename + "." + _extension;
            String relativePathImg = "/uploads/" + imgFilename;
            String finalPath = pathStatic + relativePathImg;

            System.out.println("Final image path: " + finalPath);

            // Ensure the uploads directory exists
            Path directoryPath = Paths.get(pathStatic + "/uploads");
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                System.out.println("Uploads directory created.");
            }

            // Save the image file
            Path path = Paths.get(finalPath);
            try (InputStream inputStream = confImg.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File successfully saved at: " + path.toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("Failed to save the image file.", e);
            }

            // Save the actividad in the database
            Actividad actividad = new Actividad(
                comuna,
                sector,
                nombre,
                mail,
                celular,
                dia_hora_inicio,
                dia_hora_termino,
                descripcion,
                imgFilename
            );
            actividadRepository.save(actividad);
            System.out.println("Actividad saved successfully.");
        } else {
            throw new IllegalArgumentException("Actividad validation failed.");
        }
    }
}
