package com.proyectowebs.proyectowebs.models;

// Aquí definimos variables para efectuar sobre nuestra

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    Page<Comuna> findAllByOrderByIdDesc(Pageable pageable);
}
