package com.karmatechnologiestest.karma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.karmatechnologiestest.karma.entities.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long> {

	Sales findByReferenceId(String referenceId);
}
