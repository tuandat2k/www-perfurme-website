package com.perfumeworld.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfumeworld.dto.ProductOrderDto;
import com.perfumeworld.model.ProductOrder;
import com.perfumeworld.repository.ProductOrderRepo;
import com.perfumeworld.service.ProductOrderService;


@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    @Autowired
    ProductOrderRepo productOrderRepo;

    @Override
    public void saveProductOderService(ProductOrderDto productOrderDto) {
        // TODO Auto-generated method stub
        ProductOrder productOrder = productOrderDto.convertToEntitiy();

        productOrderRepo.insert(productOrder);

    }

    @Override
    public List<ProductOrderDto> getListProductOrderDtoByOrder(Integer idOrder) {
        List<ProductOrderDto> productOrderDtos = new ArrayList<ProductOrderDto>();

        Map<String, Object> property = new HashMap<String, Object>();
        property.put("odder_id", idOrder);

        List<ProductOrder> productOrders = (List<ProductOrder>) (productOrderRepo.findByProperty(property, null, null, null, null, null)[1]);
        for (ProductOrder entity : productOrders) {
            productOrderDtos.add(entity.convertToDto());
        }
        return productOrderDtos;
    }

}
