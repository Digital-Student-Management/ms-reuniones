package com.ms_reuniones.services;
import com.ms_reuniones.models.dto.ActaRequestDTO;
import com.ms_reuniones.models.dto.ActaResponseDTO;
import com.ms_reuniones.models.entities.ActaReunionInterna;
import com.ms_reuniones.repositories.ActaReunionInternaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ActaService {
    private final ActaReunionInternaRepository actaRepo;
    private final RestTemplate restTemplate;

    //CREATE 

   // CREATE
    public ActaResponseDTO crearActa(ActaRequestDTO request) {
        // Llama a la nueva validación estricta
        validarDirectivo(request.getIdDirectivo());

        ActaReunionInterna acta = ActaReunionInterna.builder()
                .tipoReunion(request.getTipoReunion())
                .fechaReunion(LocalDateTime.now())
                .decisionesAcuerdos(request.getDecisionesAcuerdos())
                .idDirectivo(request.getIdDirectivo())
                .build();

        return mapearResponse(actaRepo.save(acta));
    }

    // READ (Todos)
    public List<ActaResponseDTO> listarTodas() {
        return actaRepo.findAll().stream().map(this::mapearResponse).toList();
    }

    // READ (Por ID)
    public ActaResponseDTO buscarPorId(Long id) {
        ActaReunionInterna acta = actaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Acta de reunión interna no encontrada."));
        return mapearResponse(acta);
    }

    // READ (Por Directivo)
    public List<ActaResponseDTO> listarPorDirectivo(Long idDirectivo) {
        return actaRepo.findByIdDirectivoOrderByFechaReunionDesc(idDirectivo)
                .stream().map(this::mapearResponse).toList();
    }

    // UPDATE
    public ActaResponseDTO actualizarActa(Long id, ActaRequestDTO request) {
        ActaReunionInterna existente = actaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Acta no encontrada para actualizar."));
        
        existente.setTipoReunion(request.getTipoReunion());
        existente.setDecisionesAcuerdos(request.getDecisionesAcuerdos());
        return mapearResponse(actaRepo.save(existente));
    }

    // DELETE
    public void eliminarActa(Long id) {
        if (!actaRepo.existsById(id)) {
            throw new RuntimeException("El acta que intenta eliminar no existe.");
        }
        actaRepo.deleteById(id);
    }

    // ============================================================
    // METODOS AUXILIARES Y DE VALIDACIÓN
    // ============================================================
    
    @SuppressWarnings("unchecked")
    private void validarDirectivo(Long id) {
        try {
            // Lee el JSON completo como un Mapa
            java.util.Map<String, Object> response = restTemplate.getForObject(
                    "http://localhost:8089/api/usuarios/" + id, 
                    java.util.Map.class
            );
            
            // Extrae el campo "tipoUsuario" que definió tu compañero en su DTO
            String tipoUsuario = (String) response.get("tipoUsuario");
            if (!"DIRECTIVO".equals(tipoUsuario)) {
                throw new RuntimeException("El ID ingresado corresponde a un " + tipoUsuario + ", no a un DIRECTIVO.");
            }
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            throw new RuntimeException("El directivo con ID " + id + " no existe en el sistema.");
        } catch (Exception e) {
            throw new RuntimeException("Error de validación con MS-Usuarios: " + e.getMessage());
        }
    }

    private ActaResponseDTO mapearResponse(ActaReunionInterna acta) {
        return ActaResponseDTO.builder()
                .idActa(acta.getIdActa())
                .tipoReunion(acta.getTipoReunion())
                .fechaReunion(acta.getFechaReunion())
                .decisionesAcuerdos(acta.getDecisionesAcuerdos())
                .idDirectivo(acta.getIdDirectivo())
                .build();
    }

}
