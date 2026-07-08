package com.ms_reuniones.services;
import com.ms_reuniones.models.dto.*;
import com.ms_reuniones.models.entities.CitacionApoderado;
import com.ms_reuniones.models.entities.ReunionGeneral;
import com.ms_reuniones.repositories.CitacionApoderadoRepository;
import com.ms_reuniones.repositories.ReunionGeneralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BitacoraService {

    private final ReunionGeneralRepository generalRepo; 
    private final CitacionApoderadoRepository citacionRepo; 
    private final RestTemplate restTemplate;

    // ==========================================
    // CRUD CITACIONES APODERADOS
    // ==========================================

   public CitacionResponseDTO crearCitacion(CitacionRequestDTO request) {
        // Validaciones estrictas por tipo de usuario
        validarRolUsuario(request.getIdDocente(), "DOCENTE");
        validarRolUsuario(request.getIdEstudiante(), "ESTUDIANTE");
        validarRolUsuario(request.getIdApoderado(), "APODERADO");

        CitacionApoderado citacion = new CitacionApoderado();
        citacion.setFechaProgramada(LocalDateTime.now().plusDays(3)); // Fecha futura sugerida
        citacion.setAcuerdosCompromisos(request.getAcuerdosCompromisos());
        citacion.setEstado(request.getEstado());
        citacion.setIdDocente(request.getIdDocente());
        citacion.setTemaEspEstudiante(request.getTemaEspEstudiante());
        citacion.setIdEstudiante(request.getIdEstudiante());
        citacion.setIdApoderado(request.getIdApoderado());

        return mapearCitacion(citacionRepo.save(citacion));
    }

    public List<CitacionResponseDTO> listarCitaciones() {
        return citacionRepo.findAll().stream().map(this::mapearCitacion).toList();
    }

    public CitacionResponseDTO buscarCitacionPorId(Long id) {
        CitacionApoderado c = citacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de citación no encontrado."));
        return mapearCitacion(c);
    }

    public CitacionResponseDTO actualizarCitacion(Long id, CitacionRequestDTO request) {
        CitacionApoderado existente = citacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Citación no encontrada para modificar."));
        
        existente.setAcuerdosCompromisos(request.getAcuerdosCompromisos());
        existente.setEstado(request.getEstado());
        existente.setTemaEspEstudiante(request.getTemaEspEstudiante());
        return mapearCitacion(citacionRepo.save(existente));
    }

    public void eliminarCitacion(Long id) {
        if (!citacionRepo.existsById(id)) {
            throw new RuntimeException("La citación no existe.");
        }
        citacionRepo.deleteById(id);
    }

    // ==========================================
    // CRUD REUNIONES GENERALES
    // ==========================================
    public ReunionGeneralResponseDTO crearReunionGeneral(ReunionGeneralRequestDTO request) {
        // Validación estricta por tipo de usuario
        validarRolUsuario(request.getIdDocente(), "DOCENTE");

        ReunionGeneral general = new ReunionGeneral();
        general.setFechaProgramada(LocalDateTime.now().plusDays(7)); // Fecha futura sugerida
        general.setAcuerdosCompromisos(request.getAcuerdosCompromisos());
        general.setEstado(request.getEstado());
        general.setIdDocente(request.getIdDocente());
        general.setTemarioGeneralCurso(request.getTemarioGeneralCurso());
        general.setIdCurso(request.getIdCurso());

        return mapearGeneral(generalRepo.save(general));
    }

    public List<ReunionGeneralResponseDTO> listarReunionesGenerales() {
        return generalRepo.findAll().stream().map(this::mapearGeneral).toList();
    }

    public ReunionGeneralResponseDTO buscarGeneralPorId(Long id) {
        ReunionGeneral rg = generalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunión general no encontrada."));
        return mapearGeneral(rg);
    }

    public ReunionGeneralResponseDTO actualizarGeneral(Long id, ReunionGeneralRequestDTO request) {
        ReunionGeneral existente = generalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunión general no encontrada para actualizar."));
        
        existente.setAcuerdosCompromisos(request.getAcuerdosCompromisos());
        existente.setEstado(request.getEstado());
        existente.setTemarioGeneralCurso(request.getTemarioGeneralCurso());
        return mapearGeneral(generalRepo.save(existente));
    }

    public void eliminarGeneral(Long id) {
        if (!generalRepo.existsById(id)) {
            throw new RuntimeException("La reunión general especificada no existe.");
        }
        generalRepo.deleteById(id);
    }

    // ============================================================
    // METODOS AUXILIARES Y DE VALIDACIÓN
    // ============================================================

    @SuppressWarnings("unchecked")
    private void validarRolUsuario(Long id, String rolEsperado) {
        try {
            java.util.Map<String, Object> response = restTemplate.getForObject(
                    "http://localhost:8089/api/usuarios/" + id, 
                    java.util.Map.class
            );
            
            String tipoUsuario = (String) response.get("tipoUsuario");
            if (!rolEsperado.equals(tipoUsuario)) {
                throw new RuntimeException("Incongruencia de roles: Se esperaba un " + rolEsperado + " pero el ID corresponde a un " + tipoUsuario);
            }
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            throw new RuntimeException("El " + rolEsperado + " con ID " + id + " no existe.");
        } catch (Exception e) {
            throw new RuntimeException("Fallo en la comunicación con MS-Usuarios: " + e.getMessage());
        }
    }

    private CitacionResponseDTO mapearCitacion(CitacionApoderado c) {
        return CitacionResponseDTO.builder()
                .idReunion(c.getIdReunion())
                .fechaProgramada(c.getFechaProgramada())
                .acuerdosCompromisos(c.getAcuerdosCompromisos())
                .estado(c.getEstado())
                .idDocente(c.getIdDocente())
                .temaEspEstudiante(c.getTemaEspEstudiante())
                .idEstudiante(c.getIdEstudiante())
                .idApoderado(c.getIdApoderado())
                .build();
    }

    private ReunionGeneralResponseDTO mapearGeneral(ReunionGeneral rg) {
        return ReunionGeneralResponseDTO.builder()
                .idReunion(rg.getIdReunion())
                .fechaProgramada(rg.getFechaProgramada())
                .acuerdosCompromisos(rg.getAcuerdosCompromisos())
                .estado(rg.getEstado())
                .idDocente(rg.getIdDocente())
                .temarioGeneralCurso(rg.getTemarioGeneralCurso())
                .idCurso(rg.getIdCurso())
                .build();
    }
}
