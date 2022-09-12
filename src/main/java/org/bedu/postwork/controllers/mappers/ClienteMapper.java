package org.bedu.postwork.controllers.mappers;


import org.bedu.postwork.persistence.entities.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente clienteModelToClienteEntity(org.bedu.postwork.model.Cliente clienteModel);

    org.bedu.postwork.model.Cliente clienteEntityToClienteModel(Cliente cliente);
}
