package org.bedu.postwork.persistence;


import org.bedu.postwork.persistence.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
