package com.polarbears.capstone.hmsfinance.service.mapper;

import com.polarbears.capstone.hmsfinance.domain.SubItems;
import com.polarbears.capstone.hmsfinance.service.dto.SubItemsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubItems} and its DTO {@link SubItemsDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubItemsMapper extends EntityMapper<SubItemsDTO, SubItems> {}
