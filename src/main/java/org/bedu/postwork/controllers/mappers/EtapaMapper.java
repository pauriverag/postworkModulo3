package org.bedu.postwork.controllers.mappers;

import org.bedu.postwork.persistence.entities.Etapa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EtapaMapper {
    Etapa  etapaModelToEtapaEntity(org.bedu.postwork.model.Etapa etapaModel);

    org.bedu.postwork.model.Etapa etapaEntityToEtapaModel(Etapa etapa);
}
