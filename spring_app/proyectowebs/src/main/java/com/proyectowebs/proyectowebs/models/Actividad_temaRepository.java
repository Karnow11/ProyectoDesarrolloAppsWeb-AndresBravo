package com.proyectowebs.proyectowebs.models;

// Aquí definimos variables para efectuar sobre nuestra

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Actividad_temaRepository extends JpaRepository<Actividad_tema, Long> {
    Page<Actividad_tema> findAllByOrderByIdDesc(Pageable pageable);
}