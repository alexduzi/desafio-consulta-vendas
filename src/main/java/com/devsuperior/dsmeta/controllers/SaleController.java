package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SalesMinDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
        SaleMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/report")
    public ResponseEntity<Page<SalesMinDTO>> getReport(@RequestParam(name = "minDate", required = false) String minDate, @RequestParam(name = "maxDate", required = false) String maxDate, @RequestParam(name = "name", defaultValue = "") String sellerName, Pageable pageable) {
        Page<SalesMinDTO> salesReport = service.getSalesReport(minDate, maxDate, sellerName, pageable);
        return ResponseEntity.ok(salesReport);
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<?> getSummary(@RequestParam(name = "minDate", required = false) String minDate, @RequestParam(name = "maxDate", required = false) String maxDate) {
        List<SalesSummaryDTO> salesSummary = service.getSalesSummary(minDate, maxDate);
        return ResponseEntity.ok(salesSummary);
    }
}
