package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.SubItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubItemsRepository extends JpaRepository<SubItems, Long>, JpaSpecificationExecutor<SubItems> {}
