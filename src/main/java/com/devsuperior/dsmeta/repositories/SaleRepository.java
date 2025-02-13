package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SalesMinDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SalesMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
            + "FROM Sale obj JOIN obj.seller "
            + "WHERE obj.date BETWEEN :initialDate AND :finalDate "
            + "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%') )")
    Page<SalesMinDTO> getSalesReport(LocalDate initialDate, LocalDate finalDate, String sellerName, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SalesSummaryDTO(obj.seller.name, SUM(obj.amount)) " +
            "FROM Sale obj JOIN obj.seller " +
            "WHERE obj.date BETWEEN :initialDate AND :finalDate " +
            "GROUP BY obj.seller.email ")
    List<SalesSummaryDTO> getSalesSummary(LocalDate initialDate, LocalDate finalDate);
}
