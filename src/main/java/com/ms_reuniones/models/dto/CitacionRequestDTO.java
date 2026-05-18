package com.ms_reuniones.models.dto;
import lombok.Data;


@Data
public class CitacionRequestDTO {
    private String acuerdosCompromisos;
    private String estado;
    private Long idDocente;
    private String temaEspEstudiante;
    private Long idEstudiante;
    private Long idApoderado;
}
