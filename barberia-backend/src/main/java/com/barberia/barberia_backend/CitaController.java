package com.barberia.barberia_backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/citas")
public class CitaController {
    // ...

    private final CitaRepository citaRepository;

    public CitaController(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    // Registrar cita
    @PostMapping
    public ResponseEntity<?> registrarCita(@RequestBody Cita cita) {
        List<Cita> existentes = citaRepository.findByBarberoAndFechaAndHora(
                cita.getBarbero(), cita.getFecha(), cita.getHora()
        );

        if (!existentes.isEmpty()) {
            return ResponseEntity.badRequest().body("Ya existe una cita con ese barbero, fecha y hora.");
        }

        Cita nuevaCita = citaRepository.save(cita);
        return ResponseEntity.ok(nuevaCita);
    }

    // Ver todas las citas
    @GetMapping
    public List<Cita> verCitas() {
        return citaRepository.findAll();
    }
    // Eliminar cita
    @DeleteMapping("/{id}")
    public void eliminarCita(@PathVariable Long id) {
        citaRepository.deleteById(id);
    }
    @PutMapping("/{id}")
    public Cita editarCita(@PathVariable Long id, @RequestBody Cita citaActualizada) {
        Cita cita = citaRepository.findById(id).orElseThrow();
        cita.setFecha(citaActualizada.getFecha());
        cita.setHora(citaActualizada.getHora());
        cita.setBarbero(citaActualizada.getBarbero());
        return citaRepository.save(cita);
    }
    // Marcar como atendida
    @PutMapping("/{id}/atender")
    public Cita marcarComoAtendida(@PathVariable Long id) {
        Cita cita = citaRepository.findById(id).orElseThrow();
        cita.setEstado("atendida");
        return citaRepository.save(cita);
    }
    // Cancelar cita
    @PutMapping("/{id}/cancelar")
    public Cita cancelarCita(@PathVariable Long id) {
        Cita cita = citaRepository.findById(id).orElseThrow();
        cita.setEstado("cancelada");
        return citaRepository.save(cita);
    }
}