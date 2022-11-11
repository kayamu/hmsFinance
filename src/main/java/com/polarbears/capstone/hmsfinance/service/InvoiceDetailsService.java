package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.InvoiceDetails;
import com.polarbears.capstone.hmsfinance.repository.InvoiceDetailsRepository;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceDetailsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.InvoiceDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InvoiceDetails}.
 */
@Service
@Transactional
public class InvoiceDetailsService {

    private final Logger log = LoggerFactory.getLogger(InvoiceDetailsService.class);

    private final InvoiceDetailsRepository invoiceDetailsRepository;

    private final InvoiceDetailsMapper invoiceDetailsMapper;

    public InvoiceDetailsService(InvoiceDetailsRepository invoiceDetailsRepository, InvoiceDetailsMapper invoiceDetailsMapper) {
        this.invoiceDetailsRepository = invoiceDetailsRepository;
        this.invoiceDetailsMapper = invoiceDetailsMapper;
    }

    /**
     * Save a invoiceDetails.
     *
     * @param invoiceDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public InvoiceDetailsDTO save(InvoiceDetailsDTO invoiceDetailsDTO) {
        log.debug("Request to save InvoiceDetails : {}", invoiceDetailsDTO);
        InvoiceDetails invoiceDetails = invoiceDetailsMapper.toEntity(invoiceDetailsDTO);
        invoiceDetails = invoiceDetailsRepository.save(invoiceDetails);
        return invoiceDetailsMapper.toDto(invoiceDetails);
    }

    /**
     * Update a invoiceDetails.
     *
     * @param invoiceDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public InvoiceDetailsDTO update(InvoiceDetailsDTO invoiceDetailsDTO) {
        log.debug("Request to update InvoiceDetails : {}", invoiceDetailsDTO);
        InvoiceDetails invoiceDetails = invoiceDetailsMapper.toEntity(invoiceDetailsDTO);
        invoiceDetails = invoiceDetailsRepository.save(invoiceDetails);
        return invoiceDetailsMapper.toDto(invoiceDetails);
    }

    /**
     * Partially update a invoiceDetails.
     *
     * @param invoiceDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InvoiceDetailsDTO> partialUpdate(InvoiceDetailsDTO invoiceDetailsDTO) {
        log.debug("Request to partially update InvoiceDetails : {}", invoiceDetailsDTO);

        return invoiceDetailsRepository
            .findById(invoiceDetailsDTO.getId())
            .map(existingInvoiceDetails -> {
                invoiceDetailsMapper.partialUpdate(existingInvoiceDetails, invoiceDetailsDTO);

                return existingInvoiceDetails;
            })
            .map(invoiceDetailsRepository::save)
            .map(invoiceDetailsMapper::toDto);
    }

    /**
     * Get all the invoiceDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InvoiceDetails");
        return invoiceDetailsRepository.findAll(pageable).map(invoiceDetailsMapper::toDto);
    }

    /**
     * Get all the invoiceDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<InvoiceDetailsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return invoiceDetailsRepository.findAllWithEagerRelationships(pageable).map(invoiceDetailsMapper::toDto);
    }

    /**
     * Get one invoiceDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceDetailsDTO> findOne(Long id) {
        log.debug("Request to get InvoiceDetails : {}", id);
        return invoiceDetailsRepository.findOneWithEagerRelationships(id).map(invoiceDetailsMapper::toDto);
    }

    /**
     * Delete the invoiceDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InvoiceDetails : {}", id);
        invoiceDetailsRepository.deleteById(id);
    }
}
