package org.bedu.postwork.persistence;


import org.bedu.postwork.persistence.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
}
