package com.ms_reuniones.models.dto;
import lombok.Data;

@Data
public class ActaRequestDTO {
    private String tipoReunion;
    private String decisionesAcuerdos;
    private Long idDirectivo;
}


