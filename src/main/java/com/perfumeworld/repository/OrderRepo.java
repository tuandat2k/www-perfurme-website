package com.perfumeworld.repository;

import java.util.List;

import com.perfumeworld.model.Order;



public interface OrderRepo extends AbstractRepo<Integer, Order> {
    List<String[]> thongKeBanHang(String tuNgay, String denNgay);
}
