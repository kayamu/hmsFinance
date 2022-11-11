package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.ConditionDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ConditionDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConditionDetailsRepository extends JpaRepository<ConditionDetails, Long>, JpaSpecificationExecutor<ConditionDetails> {}
