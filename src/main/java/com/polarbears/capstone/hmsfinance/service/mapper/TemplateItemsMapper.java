package com.polarbears.capstone.hmsfinance.service.mapper;

import com.polarbears.capstone.hmsfinance.domain.Conditions;
import com.polarbears.capstone.hmsfinance.domain.TemplateItems;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionsDTO;
import com.polarbears.capstone.hmsfinance.service.dto.TemplateItemsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TemplateItems} and its DTO {@link TemplateItemsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TemplateItemsMapper extends EntityMapper<TemplateItemsDTO, TemplateItems> {
    @Mapping(target = "conditions", source = "conditions", qualifiedByName = "conditionsName")
    TemplateItemsDTO toDto(TemplateItems s);

    @Named("conditionsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ConditionsDTO toDtoConditionsName(Conditions conditions);
}
