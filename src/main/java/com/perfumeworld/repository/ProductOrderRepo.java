package com.perfumeworld.repository;

import com.perfumeworld.model.ProductOrder;



public interface ProductOrderRepo extends AbstractRepo<Integer, ProductOrder> {
    boolean deleteProductOrder(ProductOrder productOrder);
}
