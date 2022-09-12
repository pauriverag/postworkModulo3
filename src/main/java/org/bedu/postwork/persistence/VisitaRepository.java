package org.bedu.postwork.persistence;

import org.bedu.postwork.persistence.entities.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitaRepository extends JpaRepository<Visita, Long>{
    
}
