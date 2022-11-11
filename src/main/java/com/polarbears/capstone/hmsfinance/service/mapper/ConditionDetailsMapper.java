package com.polarbears.capstone.hmsfinance.service.mapper;

import com.polarbears.capstone.hmsfinance.domain.ConditionDetails;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConditionDetails} and its DTO {@link ConditionDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConditionDetailsMapper extends EntityMapper<ConditionDetailsDTO, ConditionDetails> {}
