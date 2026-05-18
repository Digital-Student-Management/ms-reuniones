package com.ms_reuniones.repositories;
import com.ms_reuniones.models.entities.ActaReunionInterna;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActaReunionInternaRepository extends JpaRepository<ActaReunionInterna, Long> {
    List<ActaReunionInterna> findByIdDirectivoOrderByFechaReunionDesc(Long idDirectivo);
}