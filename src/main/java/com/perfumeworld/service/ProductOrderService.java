package com.perfumeworld.service;

import java.util.List;

import com.perfumeworld.dto.ProductOrderDto;


public interface ProductOrderService {
    void saveProductOderService(ProductOrderDto productOrderDto);

    List<ProductOrderDto> getListProductOrderDtoByOrder(Integer idOrder);
}
