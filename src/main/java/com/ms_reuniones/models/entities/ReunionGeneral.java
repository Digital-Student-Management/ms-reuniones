package com.ms_reuniones.models.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bitacora_reunion_general")
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class ReunionGeneral extends BitacoraReunionApo {

    @Column(length = 1000)
    private String temarioGeneralCurso;

    @Column(nullable = false)
    private Long idCurso; 
}