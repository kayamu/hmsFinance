package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.Payments;
import com.polarbears.capstone.hmsfinance.repository.PaymentsRepository;
import com.polarbears.capstone.hmsfinance.service.dto.PaymentsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.PaymentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Payments}.
 */
@Service
@Transactional
public class PaymentsService {

    private final Logger log = LoggerFactory.getLogger(PaymentsService.class);

    private final PaymentsRepository paymentsRepository;

    private final PaymentsMapper paymentsMapper;

    public PaymentsService(PaymentsRepository paymentsRepository, PaymentsMapper paymentsMapper) {
        this.paymentsRepository = paymentsRepository;
        this.paymentsMapper = paymentsMapper;
    }

    /**
     * Save a payments.
     *
     * @param paymentsDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentsDTO save(PaymentsDTO paymentsDTO) {
        log.debug("Request to save Payments : {}", paymentsDTO);
        Payments payments = paymentsMapper.toEntity(paymentsDTO);
        payments = paymentsRepository.save(payments);
        return paymentsMapper.toDto(payments);
    }

    /**
     * Update a payments.
     *
     * @param paymentsDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentsDTO update(PaymentsDTO paymentsDTO) {
        log.debug("Request to update Payments : {}", paymentsDTO);
        Payments payments = paymentsMapper.toEntity(paymentsDTO);
        payments = paymentsRepository.save(payments);
        return paymentsMapper.toDto(payments);
    }

    /**
     * Partially update a payments.
     *
     * @param paymentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentsDTO> partialUpdate(PaymentsDTO paymentsDTO) {
        log.debug("Request to partially update Payments : {}", paymentsDTO);

        return paymentsRepository
            .findById(paymentsDTO.getId())
            .map(existingPayments -> {
                paymentsMapper.partialUpdate(existingPayments, paymentsDTO);

                return existingPayments;
            })
            .map(paymentsRepository::save)
            .map(paymentsMapper::toDto);
    }

    /**
     * Get all the payments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Payments");
        return paymentsRepository.findAll(pageable).map(paymentsMapper::toDto);
    }

    /**
     * Get all the payments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PaymentsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return paymentsRepository.findAllWithEagerRelationships(pageable).map(paymentsMapper::toDto);
    }

    /**
     * Get one payments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentsDTO> findOne(Long id) {
        log.debug("Request to get Payments : {}", id);
        return paymentsRepository.findOneWithEagerRelationships(id).map(paymentsMapper::toDto);
    }

    /**
     * Delete the payments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Payments : {}", id);
        paymentsRepository.deleteById(id);
    }
}
