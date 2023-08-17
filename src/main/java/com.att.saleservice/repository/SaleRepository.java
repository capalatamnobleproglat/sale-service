package com.att.saleservice.repository;


import com.att.saleservice.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SaleRepository extends JpaRepository<Sale, Long>{
}



