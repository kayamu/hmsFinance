package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.Conditions;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ConditionsRepositoryWithBagRelationships {
    Optional<Conditions> fetchBagRelationships(Optional<Conditions> conditions);

    List<Conditions> fetchBagRelationships(List<Conditions> conditions);

    Page<Conditions> fetchBagRelationships(Page<Conditions> conditions);
}
