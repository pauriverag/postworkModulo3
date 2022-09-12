package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.persistence.entities.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    Producto productoModelToProductoEntity(org.bedu.postwork.model.Producto productoModel);

    org.bedu.postwork.model.Producto productoEntityToProductoModel(Producto producto);
}
