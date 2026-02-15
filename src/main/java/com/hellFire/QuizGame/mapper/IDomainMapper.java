package com.hellFire.QuizGame.mapper;

import com.hellFire.QuizGame.dto.DomainDto;
import com.hellFire.QuizGame.dto.request.CreateDomainRequest;
import com.hellFire.QuizGame.entity.Domain;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IDomainMapper {
    DomainDto toDto(Domain domain);
    List<DomainDto> toDtoList(List<Domain> domainList);
    Domain createEntity(CreateDomainRequest request);
}
