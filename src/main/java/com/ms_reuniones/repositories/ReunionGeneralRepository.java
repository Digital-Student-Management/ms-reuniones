package com.ms_reuniones.repositories;
import com.ms_reuniones.models.entities.ReunionGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReunionGeneralRepository extends JpaRepository<ReunionGeneral, Long> {
    List<ReunionGeneral> findByIdDocenteOrderByFechaProgramadaDesc(Long idDocente);
}
