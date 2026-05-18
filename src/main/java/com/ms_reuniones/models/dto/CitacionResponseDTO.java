package com.ms_reuniones.models.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CitacionResponseDTO {
    private Long idReunion;
    private LocalDateTime fechaProgramada;
    private String acuerdosCompromisos;
    private String estado;
    private Long idDocente;
    private String temaEspEstudiante;
    private Long idEstudiante;
    private Long idApoderado;
}
