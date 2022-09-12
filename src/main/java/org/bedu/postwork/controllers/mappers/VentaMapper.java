package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.persistence.entities.Venta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VentaMapper {
    Venta ventaModelToVentaEntity(org.bedu.postwork.model.Venta ventaModel);

    org.bedu.postwork.model.Venta ventaEntityToVentaModel(Venta venta);
}
