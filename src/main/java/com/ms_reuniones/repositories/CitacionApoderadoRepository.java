package com.ms_reuniones.repositories;
import com.ms_reuniones.models.entities.CitacionApoderado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitacionApoderadoRepository extends JpaRepository<CitacionApoderado, Long> {
    List<CitacionApoderado> findByIdDocenteOrderByFechaProgramadaDesc(Long idDocente);
}
