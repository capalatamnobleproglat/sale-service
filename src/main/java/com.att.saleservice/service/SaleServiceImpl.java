package com.att.saleservice.service;

import com.att.saleservice.client.CarClient;
import com.att.saleservice.client.UserClient;
import com.att.saleservice.dto.CarDto;
import com.att.saleservice.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import com.att.saleservice.model.Sale;
import com.att.saleservice.dto.SaleDto;
import com.att.saleservice.repository.SaleRepository;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final UserClient userClient;
    private final CarClient carClient;

    public SaleServiceImpl(SaleRepository saleRepository, UserClient userClient, CarClient carClient) {
        this.saleRepository = saleRepository;
        this.userClient = userClient;
        this.carClient = carClient;
    }

    @Override
    public List<SaleDto> getAllSale() {
        List<Sale> sales = saleRepository.findAll();
        return sales.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SaleDto createSale(SaleDto saleDto) {
        // Verify that user and car exist
        UserDto userDto = userClient.getUser(saleDto.getUserId());
        CarDto carDto = carClient.getCar(saleDto.getCarId());

        // If user and car do not exist, throw an exception
        if(userDto == null || carDto == null) {
            throw new RuntimeException("User or car does not exist.");
        }

        Sale sale = new Sale();
        sale.setUserId(saleDto.getUserId());
        sale.setCarId(saleDto.getCarId());
        sale.setQuantity(saleDto.getQuantity());
        sale.setSaleDate(saleDto.getSaleDate());

        sale = saleRepository.save(sale);

        return convertToDto(sale);
    }

    @Override
    public SaleDto getSaleById(Long id) throws ChangeSetPersister.NotFoundException {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return convertToDto(sale);
    }

    @Override
    public SaleDto updateSale(Long id, SaleDto saleDto) throws ChangeSetPersister.NotFoundException {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Actualizar los campos de la venta existente con la información del DTO
        BeanUtils.copyProperties(saleDto, existingSale, "id");

        existingSale = saleRepository.save(existingSale);
        return convertToDto(existingSale);
    }

    @Override
    public void deleteSale(Long id) throws ChangeSetPersister.NotFoundException {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        saleRepository.delete(sale);
    }

    private SaleDto convertToDto(Sale sale) {
        SaleDto saleDto = new SaleDto();
        BeanUtils.copyProperties(sale, saleDto);
        return saleDto;
    }

    private Sale convertToEntity(SaleDto saleDto) {
        Sale sale = new Sale();
        BeanUtils.copyProperties(saleDto, sale);
        return sale;
    }
}
