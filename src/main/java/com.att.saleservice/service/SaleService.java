package com.att.saleservice.service;

import com.att.saleservice.dto.SaleDto;
import org.springframework.data.crossstore.ChangeSetPersister;
import java.util.List;

public interface SaleService {

    List<SaleDto> getAllSale();

    SaleDto getSaleById(Long id) throws ChangeSetPersister.NotFoundException;

    SaleDto createSale(SaleDto saleDto);

    SaleDto updateSale(Long id, SaleDto saleDto) throws ChangeSetPersister.NotFoundException;

    void deleteSale(Long id) throws ChangeSetPersister.NotFoundException;
}
