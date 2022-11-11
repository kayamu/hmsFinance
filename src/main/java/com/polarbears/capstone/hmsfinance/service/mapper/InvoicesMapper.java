package com.polarbears.capstone.hmsfinance.service.mapper;

import com.polarbears.capstone.hmsfinance.domain.InvoiceDetails;
import com.polarbears.capstone.hmsfinance.domain.Invoices;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceDetailsDTO;
import com.polarbears.capstone.hmsfinance.service.dto.InvoicesDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invoices} and its DTO {@link InvoicesDTO}.
 */
@Mapper(componentModel = "spring")
public interface InvoicesMapper extends EntityMapper<InvoicesDTO, Invoices> {
    @Mapping(target = "invoiceDetails", source = "invoiceDetails", qualifiedByName = "invoiceDetailsIdSet")
    InvoicesDTO toDto(Invoices s);

    @Mapping(target = "removeInvoiceDetails", ignore = true)
    Invoices toEntity(InvoicesDTO invoicesDTO);

    @Named("invoiceDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InvoiceDetailsDTO toDtoInvoiceDetailsId(InvoiceDetails invoiceDetails);

    @Named("invoiceDetailsIdSet")
    default Set<InvoiceDetailsDTO> toDtoInvoiceDetailsIdSet(Set<InvoiceDetails> invoiceDetails) {
        return invoiceDetails.stream().map(this::toDtoInvoiceDetailsId).collect(Collectors.toSet());
    }
}
