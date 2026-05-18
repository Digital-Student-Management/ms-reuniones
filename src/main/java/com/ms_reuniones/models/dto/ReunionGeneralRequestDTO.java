package com.ms_reuniones.models.dto;
import lombok.Data;

@Data
public class ReunionGeneralRequestDTO {
    private String acuerdosCompromisos;
    private String estado;
    private Long idDocente;
    private String temarioGeneralCurso;
    private Long idCurso;
}