package com.barberia.barberia_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByBarberoAndFechaAndHora(String barbero, String fecha, String hora);
}