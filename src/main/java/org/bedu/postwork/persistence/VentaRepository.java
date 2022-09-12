package org.bedu.postwork.persistence;


import org.bedu.postwork.persistence.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long>{
    
}
