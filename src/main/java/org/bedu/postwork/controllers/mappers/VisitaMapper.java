package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.persistence.entities.Visita;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitaMapper {
    Visita visitaModelToVisitaEntity(org.bedu.postwork.model.Visita visitaModel);

    org.bedu.postwork.model.Visita visitaEntityToVisitaModel(Visita visita);
}
