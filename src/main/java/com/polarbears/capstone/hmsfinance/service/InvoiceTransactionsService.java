package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.InvoiceTransactions;
import com.polarbears.capstone.hmsfinance.repository.InvoiceTransactionsRepository;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceTransactionsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.InvoiceTransactionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InvoiceTransactions}.
 */
@Service
@Transactional
public class InvoiceTransactionsService {

    private final Logger log = LoggerFactory.getLogger(InvoiceTransactionsService.class);

    private final InvoiceTransactionsRepository invoiceTransactionsRepository;

    private final InvoiceTransactionsMapper invoiceTransactionsMapper;

    public InvoiceTransactionsService(
        InvoiceTransactionsRepository invoiceTransactionsRepository,
        InvoiceTransactionsMapper invoiceTransactionsMapper
    ) {
        this.invoiceTransactionsRepository = invoiceTransactionsRepository;
        this.invoiceTransactionsMapper = invoiceTransactionsMapper;
    }

    /**
     * Save a invoiceTransactions.
     *
     * @param invoiceTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    public InvoiceTransactionsDTO save(InvoiceTransactionsDTO invoiceTransactionsDTO) {
        log.debug("Request to save InvoiceTransactions : {}", invoiceTransactionsDTO);
        InvoiceTransactions invoiceTransactions = invoiceTransactionsMapper.toEntity(invoiceTransactionsDTO);
        invoiceTransactions = invoiceTransactionsRepository.save(invoiceTransactions);
        return invoiceTransactionsMapper.toDto(invoiceTransactions);
    }

    /**
     * Update a invoiceTransactions.
     *
     * @param invoiceTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    public InvoiceTransactionsDTO update(InvoiceTransactionsDTO invoiceTransactionsDTO) {
        log.debug("Request to update InvoiceTransactions : {}", invoiceTransactionsDTO);
        InvoiceTransactions invoiceTransactions = invoiceTransactionsMapper.toEntity(invoiceTransactionsDTO);
        invoiceTransactions = invoiceTransactionsRepository.save(invoiceTransactions);
        return invoiceTransactionsMapper.toDto(invoiceTransactions);
    }

    /**
     * Partially update a invoiceTransactions.
     *
     * @param invoiceTransactionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InvoiceTransactionsDTO> partialUpdate(InvoiceTransactionsDTO invoiceTransactionsDTO) {
        log.debug("Request to partially update InvoiceTransactions : {}", invoiceTransactionsDTO);

        return invoiceTransactionsRepository
            .findById(invoiceTransactionsDTO.getId())
            .map(existingInvoiceTransactions -> {
                invoiceTransactionsMapper.partialUpdate(existingInvoiceTransactions, invoiceTransactionsDTO);

                return existingInvoiceTransactions;
            })
            .map(invoiceTransactionsRepository::save)
            .map(invoiceTransactionsMapper::toDto);
    }

    /**
     * Get all the invoiceTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceTransactionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InvoiceTransactions");
        return invoiceTransactionsRepository.findAll(pageable).map(invoiceTransactionsMapper::toDto);
    }

    /**
     * Get all the invoiceTransactions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<InvoiceTransactionsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return invoiceTransactionsRepository.findAllWithEagerRelationships(pageable).map(invoiceTransactionsMapper::toDto);
    }

    /**
     * Get one invoiceTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceTransactionsDTO> findOne(Long id) {
        log.debug("Request to get InvoiceTransactions : {}", id);
        return invoiceTransactionsRepository.findOneWithEagerRelationships(id).map(invoiceTransactionsMapper::toDto);
    }

    /**
     * Delete the invoiceTransactions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InvoiceTransactions : {}", id);
        invoiceTransactionsRepository.deleteById(id);
    }
}
