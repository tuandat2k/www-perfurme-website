package com.perfumeworld.controller.admin.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perfumeworld.service.OrderService;
import com.perfumeworld.service.ProductOrderService;
import com.perfumeworld.service.ProductService;



@Controller
@RequestMapping("/admin/api/v1/order")
public class OrderApi {
    @Autowired
    OrderService orderService;

    @Autowired
    ProductOrderService productOrderService;

    @Autowired
    ProductService productService;

    @RequestMapping(path = "/{idOrder}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable int idOrder) {
        if (orderService.deleteOrderService(idOrder)) {
            return "success";
        } else {
            return "fail";
        }

    }

    @RequestMapping(path = "/{idOrder}", method = RequestMethod.PUT)
    @ResponseBody
    public String update(@PathVariable int idOrder) {

        if (orderService.updateOrderByStatus(true, idOrder)) {

            return "success";
        }
        return "fail";

    }
}
