package com.polarbears.capstone.hmsfinance.service.mapper;

import com.polarbears.capstone.hmsfinance.domain.TemplateItems;
import com.polarbears.capstone.hmsfinance.domain.Templates;
import com.polarbears.capstone.hmsfinance.service.dto.TemplateItemsDTO;
import com.polarbears.capstone.hmsfinance.service.dto.TemplatesDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Templates} and its DTO {@link TemplatesDTO}.
 */
@Mapper(componentModel = "spring")
public interface TemplatesMapper extends EntityMapper<TemplatesDTO, Templates> {
    @Mapping(target = "templateItems", source = "templateItems", qualifiedByName = "templateItemsNameSet")
    TemplatesDTO toDto(Templates s);

    @Mapping(target = "removeTemplateItems", ignore = true)
    Templates toEntity(TemplatesDTO templatesDTO);

    @Named("templateItemsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TemplateItemsDTO toDtoTemplateItemsName(TemplateItems templateItems);

    @Named("templateItemsNameSet")
    default Set<TemplateItemsDTO> toDtoTemplateItemsNameSet(Set<TemplateItems> templateItems) {
        return templateItems.stream().map(this::toDtoTemplateItemsName).collect(Collectors.toSet());
    }
}
