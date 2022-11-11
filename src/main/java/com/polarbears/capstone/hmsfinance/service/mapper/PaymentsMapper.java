package com.polarbears.capstone.hmsfinance.service.mapper;

import com.polarbears.capstone.hmsfinance.domain.Invoices;
import com.polarbears.capstone.hmsfinance.domain.Payments;
import com.polarbears.capstone.hmsfinance.service.dto.InvoicesDTO;
import com.polarbears.capstone.hmsfinance.service.dto.PaymentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payments} and its DTO {@link PaymentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentsMapper extends EntityMapper<PaymentsDTO, Payments> {
    @Mapping(target = "invoices", source = "invoices", qualifiedByName = "invoicesInvoiceNumber")
    PaymentsDTO toDto(Payments s);

    @Named("invoicesInvoiceNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "invoiceNumber", source = "invoiceNumber")
    InvoicesDTO toDtoInvoicesInvoiceNumber(Invoices invoices);
}
