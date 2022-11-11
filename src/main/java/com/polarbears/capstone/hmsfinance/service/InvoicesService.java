package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.Invoices;
import com.polarbears.capstone.hmsfinance.repository.InvoicesRepository;
import com.polarbears.capstone.hmsfinance.service.dto.InvoicesDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.InvoicesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Invoices}.
 */
@Service
@Transactional
public class InvoicesService {

    private final Logger log = LoggerFactory.getLogger(InvoicesService.class);

    private final InvoicesRepository invoicesRepository;

    private final InvoicesMapper invoicesMapper;

    public InvoicesService(InvoicesRepository invoicesRepository, InvoicesMapper invoicesMapper) {
        this.invoicesRepository = invoicesRepository;
        this.invoicesMapper = invoicesMapper;
    }

    /**
     * Save a invoices.
     *
     * @param invoicesDTO the entity to save.
     * @return the persisted entity.
     */
    public InvoicesDTO save(InvoicesDTO invoicesDTO) {
        log.debug("Request to save Invoices : {}", invoicesDTO);
        Invoices invoices = invoicesMapper.toEntity(invoicesDTO);
        invoices = invoicesRepository.save(invoices);
        return invoicesMapper.toDto(invoices);
    }

    /**
     * Update a invoices.
     *
     * @param invoicesDTO the entity to save.
     * @return the persisted entity.
     */
    public InvoicesDTO update(InvoicesDTO invoicesDTO) {
        log.debug("Request to update Invoices : {}", invoicesDTO);
        Invoices invoices = invoicesMapper.toEntity(invoicesDTO);
        invoices = invoicesRepository.save(invoices);
        return invoicesMapper.toDto(invoices);
    }

    /**
     * Partially update a invoices.
     *
     * @param invoicesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InvoicesDTO> partialUpdate(InvoicesDTO invoicesDTO) {
        log.debug("Request to partially update Invoices : {}", invoicesDTO);

        return invoicesRepository
            .findById(invoicesDTO.getId())
            .map(existingInvoices -> {
                invoicesMapper.partialUpdate(existingInvoices, invoicesDTO);

                return existingInvoices;
            })
            .map(invoicesRepository::save)
            .map(invoicesMapper::toDto);
    }

    /**
     * Get all the invoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoicesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Invoices");
        return invoicesRepository.findAll(pageable).map(invoicesMapper::toDto);
    }

    /**
     * Get all the invoices with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<InvoicesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return invoicesRepository.findAllWithEagerRelationships(pageable).map(invoicesMapper::toDto);
    }

    /**
     * Get one invoices by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InvoicesDTO> findOne(Long id) {
        log.debug("Request to get Invoices : {}", id);
        return invoicesRepository.findOneWithEagerRelationships(id).map(invoicesMapper::toDto);
    }

    /**
     * Delete the invoices by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Invoices : {}", id);
        invoicesRepository.deleteById(id);
    }
}
