package com.polarbears.capstone.hmsfinance.service.mapper;

import com.polarbears.capstone.hmsfinance.domain.ConditionDetails;
import com.polarbears.capstone.hmsfinance.domain.Conditions;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionDetailsDTO;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionsDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conditions} and its DTO {@link ConditionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConditionsMapper extends EntityMapper<ConditionsDTO, Conditions> {
    @Mapping(target = "conditionDetails", source = "conditionDetails", qualifiedByName = "conditionDetailsNameSet")
    ConditionsDTO toDto(Conditions s);

    @Mapping(target = "removeConditionDetails", ignore = true)
    Conditions toEntity(ConditionsDTO conditionsDTO);

    @Named("conditionDetailsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ConditionDetailsDTO toDtoConditionDetailsName(ConditionDetails conditionDetails);

    @Named("conditionDetailsNameSet")
    default Set<ConditionDetailsDTO> toDtoConditionDetailsNameSet(Set<ConditionDetails> conditionDetails) {
        return conditionDetails.stream().map(this::toDtoConditionDetailsName).collect(Collectors.toSet());
    }
}
