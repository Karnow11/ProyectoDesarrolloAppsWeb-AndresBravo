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

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.proyectowebs.proyectowebs.models.Actividad;
import com.proyectowebs.proyectowebs.models.ActividadRepository;

@Service
public class AppService {

    private final String pathStatic;
    private final ActividadRepository actividadRepository;

    public AppService(ActividadRepository actividadRepository) throws IOException {
        this.actividadRepository = actividadRepository;
        // Dynamically resolve the absolute path for the static directory
        Path staticDir = Paths.get(ResourceUtils.getFile("classpath:static").getAbsolutePath());
        this.pathStatic = staticDir.toString();
        System.out.println("Static path resolved to: " + this.pathStatic);
    }

    public List<Map<String, String>> getActividadesData(Integer pageSize) {
        List<Actividad> Actividades = actividadRepository.findAllByOrderByIdDesc(PageRequest.of(0, pageSize)).getContent();
        List<Map<String, String>> actividadesData = new ArrayList<>();
        
        for (Actividad act : Actividades) {
            Map<String, String> actividadData = new HashMap<>();
            actividadData.put("id", act.getId().toString());
            actividadData.put("comuna", Integer.toString(act.getComuna()));
            actividadData.put("sector", act.getSector().toString());
            actividadData.put("nombre", act.getNombre().toString());
            actividadData.put("mail", act.getMail().toString());
            actividadData.put("celular", act.getCelular().toString());
            actividadData.put("inicio", act.getDia_hora_inicio().toString());
            actividadData.put("termino", act.getDia_hora_termino().toString());
            actividadData.put("descripcion", act.getDescripcion().toString());
            actividadData.put("image_filename", act.getImg());

            actividadesData.add(actividadData);
        }
        return actividadesData;
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
