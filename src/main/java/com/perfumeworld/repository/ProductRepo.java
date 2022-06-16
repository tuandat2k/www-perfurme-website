package com.perfumeworld.repository;

import java.util.List;

import com.perfumeworld.model.Product;



public interface ProductRepo extends AbstractRepo<Integer, Product> {
    List<Product> getListSpKhuyenMai(int offset, int limit);

    int getCountSpKhuyenMai();
}
