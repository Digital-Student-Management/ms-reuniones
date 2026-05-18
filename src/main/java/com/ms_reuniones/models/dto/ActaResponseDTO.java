package com.ms_reuniones.models.dto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data 
@Builder
public class ActaResponseDTO {
    private Long idActa;
    private String tipoReunion;
    private LocalDateTime fechaReunion;
    private String decisionesAcuerdos;
    private Long idDirectivo;
}