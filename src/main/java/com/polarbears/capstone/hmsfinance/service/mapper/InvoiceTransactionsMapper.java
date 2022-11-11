package com.polarbears.capstone.hmsfinance.service.mapper;

import com.polarbears.capstone.hmsfinance.domain.InvoiceTransactions;
import com.polarbears.capstone.hmsfinance.domain.Invoices;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceTransactionsDTO;
import com.polarbears.capstone.hmsfinance.service.dto.InvoicesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InvoiceTransactions} and its DTO {@link InvoiceTransactionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface InvoiceTransactionsMapper extends EntityMapper<InvoiceTransactionsDTO, InvoiceTransactions> {
    @Mapping(target = "invoices", source = "invoices", qualifiedByName = "invoicesInvoiceNumber")
    InvoiceTransactionsDTO toDto(InvoiceTransactions s);

    @Named("invoicesInvoiceNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "invoiceNumber", source = "invoiceNumber")
    InvoicesDTO toDtoInvoicesInvoiceNumber(Invoices invoices);
}
