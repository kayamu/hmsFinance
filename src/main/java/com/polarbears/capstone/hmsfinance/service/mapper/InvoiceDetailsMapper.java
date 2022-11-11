package com.polarbears.capstone.hmsfinance.service.mapper;

import com.polarbears.capstone.hmsfinance.domain.InvoiceDetails;
import com.polarbears.capstone.hmsfinance.domain.SubItems;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceDetailsDTO;
import com.polarbears.capstone.hmsfinance.service.dto.SubItemsDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InvoiceDetails} and its DTO {@link InvoiceDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface InvoiceDetailsMapper extends EntityMapper<InvoiceDetailsDTO, InvoiceDetails> {
    @Mapping(target = "subItems", source = "subItems", qualifiedByName = "subItemsNameSet")
    InvoiceDetailsDTO toDto(InvoiceDetails s);

    @Mapping(target = "removeSubItems", ignore = true)
    InvoiceDetails toEntity(InvoiceDetailsDTO invoiceDetailsDTO);

    @Named("subItemsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SubItemsDTO toDtoSubItemsName(SubItems subItems);

    @Named("subItemsNameSet")
    default Set<SubItemsDTO> toDtoSubItemsNameSet(Set<SubItems> subItems) {
        return subItems.stream().map(this::toDtoSubItemsName).collect(Collectors.toSet());
    }
}
