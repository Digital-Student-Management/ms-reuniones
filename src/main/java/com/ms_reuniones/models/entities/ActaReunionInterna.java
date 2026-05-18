package com.ms_reuniones.models.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "acta_reunion_interna")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder

public class ActaReunionInterna {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActa;

    @Column(nullable = false, length = 100)
    private String tipoReunion;

    @Column(nullable = false)
    private LocalDateTime fechaReunion;

    @Column(length = 2000)
    private String decisionesAcuerdos;

    // Relación 1 a N: Un directivo hace muchas actas (Referencia Suave a ms-usuarios)
    @Column(nullable = false)
    private Long idDirectivo;
}