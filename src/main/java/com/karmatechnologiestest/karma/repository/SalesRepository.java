package com.karmatechnologiestest.karma.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.karmatechnologiestest.karma.entities.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long> {

    @Query("SELECT s FROM Sales s WHERE LOWER(s.referenceId) LIKE %:query% " +
           "OR LOWER(s.status) LIKE %:query% " +
           "OR LOWER(s.paymentStatus) LIKE %:query% " +
           "OR LOWER(s.biller) LIKE %:query%")
    List<Sales> findBySalesDetails(@Param("query") String query);
}
