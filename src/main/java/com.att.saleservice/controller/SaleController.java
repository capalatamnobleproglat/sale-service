package com.att.saleservice.controller;

import com.att.saleservice.dto.SaleDto;
import com.att.saleservice.service.SaleService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/sales")
public class SaleController  {
    private static final Logger logger = LoggerFactory.getLogger(SaleController.class);

    @Value("${example.property}")
    private String exampleProperty;

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<SaleDto> getAllSales() {
        return saleService.getAllSale();
    }

    @CircuitBreaker(name = "createOrderCB", fallbackMethod = "fallBackCreateOrder")
    @PostMapping
    public SaleDto createSale(@RequestBody SaleDto saleDto) { return saleService.createSale(saleDto); }

    @GetMapping("/{id}")
    public SaleDto getSaleById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {

        logger.info("exampleProperty: {}", exampleProperty);
        return saleService.getSaleById(id);
    }

    @PutMapping("/{id}")
    public SaleDto updateSale(@PathVariable Long id, @RequestBody SaleDto saleDto) throws ChangeSetPersister.NotFoundException {
        return saleService.updateSale(id, saleDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        saleService.deleteSale(id);
    }

    public SaleDto fallBackCreateSale(SaleDto saleDto, Exception e) {
        SaleDto defaultSale = new SaleDto();
        defaultSale.setId(-1L);
        defaultSale.setUserId(saleDto.getUserId());
        defaultSale.setCarId(-1L);
        defaultSale.setQuantity(0);
        defaultSale.setSaleDate(new Date());

        return defaultSale;
    }
}
