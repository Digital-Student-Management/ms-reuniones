package com.ms_reuniones.controllers;
import com.ms_reuniones.models.dto.*;
import com.ms_reuniones.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/reuniones")
@RequiredArgsConstructor

public class ReunionesController {

    private final ActaService actaService;
    private final BitacoraService bitacoraService;

    // ============================================================
    // 📑 CRUD 1: ACTAS DE REUNIONES INTERNAS (DIRECTIVOS)
    // ============================================================
    
    @PostMapping("/actas")
    public ResponseEntity<ActaResponseDTO> crearActa(@Valid @RequestBody ActaRequestDTO request) {
        return new ResponseEntity<>(actaService.crearActa(request), HttpStatus.CREATED);
    }

    @GetMapping("/actas")
    public ResponseEntity<List<ActaResponseDTO>> listarTodasLasActas() {
        return ResponseEntity.ok(actaService.listarTodas());
    }

    @GetMapping("/actas/{id}")
    public ResponseEntity<ActaResponseDTO> buscarActaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(actaService.buscarPorId(id));
    }

    @GetMapping("/actas/directivo/{idDirectivo}")
    public ResponseEntity<List<ActaResponseDTO>> listarActasPorDirectivo(@PathVariable Long idDirectivo) {
        return ResponseEntity.ok(actaService.listarPorDirectivo(idDirectivo));
    }

    @PutMapping("/actas/{id}")
    public ResponseEntity<ActaResponseDTO> actualizarActa(@PathVariable Long id, @Valid @RequestBody ActaRequestDTO request) {
        return ResponseEntity.ok(actaService.actualizarActa(id, request));
    }

    @DeleteMapping("/actas/{id}")
    public ResponseEntity<String> eliminarActa(@PathVariable Long id) {
        actaService.eliminarActa(id);
        return ResponseEntity.ok("Acta de reunión interna eliminada correctamente.");
    }

    // ============================================================
    // 👤 CRUD 2: BITÁCORA DE CITACIONES (APODERADOS INDIVIDUALES)
    // ============================================================

    @PostMapping("/bitacoras/citaciones")
    public ResponseEntity<CitacionResponseDTO> crearCitacion(@Valid @RequestBody CitacionRequestDTO request) {
        return new ResponseEntity<>(bitacoraService.crearCitacion(request), HttpStatus.CREATED);
    }

    @GetMapping("/bitacoras/citaciones")
    public ResponseEntity<List<CitacionResponseDTO>> listarTodasLasCitaciones() {
        return ResponseEntity.ok(bitacoraService.listarCitaciones());
    }

    @GetMapping("/bitacoras/citaciones/{id}")
    public ResponseEntity<CitacionResponseDTO> buscarCitacionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(bitacoraService.buscarCitacionPorId(id));
    }

    @PutMapping("/bitacoras/citaciones/{id}")
    public ResponseEntity<CitacionResponseDTO> actualizarCitacion(@PathVariable Long id, @Valid @RequestBody CitacionRequestDTO request) {
        return ResponseEntity.ok(bitacoraService.actualizarCitacion(id, request));
    }

    @DeleteMapping("/bitacoras/citaciones/{id}")
    public ResponseEntity<String> eliminarCitacion(@PathVariable Long id) {
        bitacoraService.eliminarCitacion(id);
        return ResponseEntity.ok("Registro de citación individual eliminado.");
    }

    // ============================================================
    // 👥 CRUD 3: BITÁCORA DE REUNIONES GENERALES (CURSOS)
    // ============================================================

    @PostMapping("/bitacoras/generales")
    public ResponseEntity<ReunionGeneralResponseDTO> crearReunionGeneral(@Valid @RequestBody ReunionGeneralRequestDTO request) {
        return new ResponseEntity<>(bitacoraService.crearReunionGeneral(request), HttpStatus.CREATED);
    }

    @GetMapping("/bitacoras/generales")
    public ResponseEntity<List<ReunionGeneralResponseDTO>> listarTodasLasReunionesGenerales() {
        return ResponseEntity.ok(bitacoraService.listarReunionesGenerales());
    }

    @GetMapping("/bitacoras/generales/{id}")
    public ResponseEntity<ReunionGeneralResponseDTO> buscarGeneralPorId(@PathVariable Long id) {
        return ResponseEntity.ok(bitacoraService.buscarGeneralPorId(id));
    }

    @PutMapping("/bitacoras/generales/{id}")
    public ResponseEntity<ReunionGeneralResponseDTO> actualizarGeneral(@PathVariable Long id, @Valid @RequestBody ReunionGeneralRequestDTO request) {
        return ResponseEntity.ok(bitacoraService.actualizarGeneral(id, request));
    }

    @DeleteMapping("/bitacoras/generales/{id}")
    public ResponseEntity<String> eliminarGeneral(@PathVariable Long id) {
        bitacoraService.eliminarGeneral(id);
        return ResponseEntity.ok("Registro de reunión general de curso eliminado.");
    }




}
