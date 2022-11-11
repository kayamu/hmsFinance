package com.polarbears.capstone.hmsfinance.service.mapper;

import com.polarbears.capstone.hmsfinance.domain.Items;
import com.polarbears.capstone.hmsfinance.domain.Templates;
import com.polarbears.capstone.hmsfinance.service.dto.ItemsDTO;
import com.polarbears.capstone.hmsfinance.service.dto.TemplatesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Items} and its DTO {@link ItemsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemsMapper extends EntityMapper<ItemsDTO, Items> {
    @Mapping(target = "templates", source = "templates", qualifiedByName = "templatesName")
    ItemsDTO toDto(Items s);

    @Named("templatesName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TemplatesDTO toDtoTemplatesName(Templates templates);
}
