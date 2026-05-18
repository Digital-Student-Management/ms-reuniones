package com.ms_reuniones.models.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora_reuniones_apo")
@Inheritance(strategy = InheritanceType.JOINED) // Estrategia de herencia para las subclases
@Data 
@NoArgsConstructor
@AllArgsConstructor
public abstract class BitacoraReunionApo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReunion;

    @Column(nullable = false)
    private LocalDateTime fechaProgramada;

    @Column(length = 2000)
    private String acuerdosCompromisos;

    @Column(nullable = false, length = 50)
    private String estado; // Ej: PROGRAMADA, REALIZADA, CANCELADA

    // Relación 1 a N: Un docente tiene muchas bitácoras (Referencia Suave a ms-usuarios)
    @Column(nullable = false)
    private Long idDocente;
}