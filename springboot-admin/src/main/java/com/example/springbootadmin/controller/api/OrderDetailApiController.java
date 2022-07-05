package com.admin.springbootadmin.controller.api;

import com.admin.springbootadmin.controller.CRUDController;
import com.admin.springbootadmin.model.entity.OrderDetail;
import com.admin.springbootadmin.model.network.request.OrderDetailApiRequest;
import com.admin.springbootadmin.model.network.response.OrderDetailApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailApiController extends CRUDController<OrderDetailApiRequest, OrderDetailApiResponse, OrderDetail> {
}
