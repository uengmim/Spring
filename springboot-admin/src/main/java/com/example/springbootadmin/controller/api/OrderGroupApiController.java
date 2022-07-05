package com.admin.springbootadmin.controller.api;

import com.admin.springbootadmin.controller.CRUDController;
import com.admin.springbootadmin.model.entity.OrderGroup;
import com.admin.springbootadmin.model.network.request.OrderGroupApiRequest;
import com.admin.springbootadmin.model.network.response.OrderGroupApiResponse;
import com.admin.springbootadmin.service.OrderGroupApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping("/api/orderGroup")
public class OrderGroupApiController extends CRUDController<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

    @Autowired
    private OrderGroupApiService orderGroupApiService;

    @PostConstruct
    public void init(){
        this.baseService = orderGroupApiService;
    }

}
