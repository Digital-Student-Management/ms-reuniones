package com.ms_reuniones.models.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bitacora_citacion_apoderado")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // llama a superclase para comparar campos heredados

public class CitacionApoderado extends BitacoraReunionApo {

    @Column(length = 500)
    private String temaEspEstudiante;

    // Referencias Suaves a Estudiante y Apoderado
    @Column(nullable = false)
    private Long idEstudiante;

    @Column(nullable = false)
    private Long idApoderado;
}