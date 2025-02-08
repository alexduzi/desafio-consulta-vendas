package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SalesMinDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    @Transactional(readOnly = true)
    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<SalesMinDTO> getSalesReport(String minDate, String maxDate, String sellerName, Pageable pageable) {
        LocalDate initialDate = LocalDate.now().minusYears(1L);
        LocalDate finalDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

        if (minDate != null) {
            initialDate = LocalDate.parse(minDate, getDateTimeFormatter());
        }

        if (maxDate != null) {
            finalDate = LocalDate.parse(maxDate, getDateTimeFormatter());
        }

        return repository.getSalesReport(initialDate, finalDate, sellerName, pageable);
    }

    @Transactional(readOnly = true)
    public SalesSummaryDTO getSalesSummary(String minDate, String maxDate) {

        return new SalesSummaryDTO();
    }

    private DateTimeFormatter getDateTimeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.US);
        return formatter;
    }
}
