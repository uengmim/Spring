package com.admin.springbootadmin.service;

import com.admin.springbootadmin.model.entity.OrderDetail;
import com.admin.springbootadmin.model.network.Header;
import com.admin.springbootadmin.model.network.request.OrderDetailApiRequest;
import com.admin.springbootadmin.model.network.response.OrderDetailApiResponse;
import com.admin.springbootadmin.repository.ItemRepository;
import com.admin.springbootadmin.repository.OrderGroupRepository;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailApiService extends BaseService<OrderDetailApiRequest, OrderDetailApiResponse, OrderDetail> {

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest orderDetailApiRequest = request.getData();

        OrderDetail orderDetail = OrderDetail.builder()
                .status(orderDetailApiRequest.getStatus())
                .arrivalDate(orderDetailApiRequest.getArrivalDate())
                .quantity(orderDetailApiRequest.getQuantity())
                .totalPrice(orderDetailApiRequest.getTotalPrice())
                .createdAt(LocalDateTime.now())
                .createdBy(orderDetailApiRequest.getCreatedBy())
                .orderGroup(orderGroupRepository.getOne(orderDetailApiRequest.getOrderGroupId()))
                .item(itemRepository.getOne(orderDetailApiRequest.getItemId()))
                .build();

        OrderDetail savedOrderDetail = baseRespository.save(orderDetail);

        return Header.OK(response(savedOrderDetail));
    }

    @Override
    public Header<OrderDetailApiResponse> read(Long id) {
        return baseRespository.findById(id)
                .map(orderDetail -> Header.OK(response(orderDetail)))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest orderDetailApiRequest = request.getData();

        return baseRespository.findById(orderDetailApiRequest.getId())
                .map(orderDetail -> {
                    orderDetail
                            .setStatus(orderDetailApiRequest.getStatus())
                            .setArrivalDate(orderDetailApiRequest.getArrivalDate())
                            .setQuantity(orderDetailApiRequest.getQuantity())
                            .setTotalPrice(orderDetailApiRequest.getTotalPrice())
                            .setCreatedAt(orderDetailApiRequest.getCreatedAt())
                            .setCreatedBy(orderDetailApiRequest.getCreatedBy())
                            .setUpdatedAt(orderDetailApiRequest.getUpdatedAt())
                            .setUpdatedBy(orderDetailApiRequest.getUpdatedBy())
                            .setOrderGroup(orderGroupRepository.getOne(orderDetailApiRequest.getOrderGroupId()))
                            .setItem(itemRepository.getOne(orderDetailApiRequest.getItemId()));

                    OrderDetail newOrderDetail = baseRespository.save(orderDetail);
                    return newOrderDetail;
                })
                .map(orderDetail -> Header.OK(response(orderDetail)))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<List<OrderDetailApiResponse>> search(Pageable pageable) {
        Page<OrderDetail> orderDetails = baseRespository.findAll(pageable);

        List<OrderDetailApiResponse> orderDetailApiResponseList = orderDetails.stream()
                .map(orderDetail -> response(orderDetail))
                .collect(Collectors.toList());

        return Header.OK(orderDetailApiResponseList);
    }

    @Override
    public Header delete(Long id) {
        return baseRespository.findById(id)
                .map(orderDetail -> {
                    baseRespository.delete(orderDetail);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private OrderDetailApiResponse response(OrderDetail orderDetail){

        OrderDetailApiResponse response = OrderDetailApiResponse.builder()
                .id(orderDetail.getId())
                .itemId(orderDetail.getItem().getId())
                .orderGroupId(orderDetail.getOrderGroup().getId())
                .status(orderDetail.getStatus())
                .arrivalDate(orderDetail.getArrivalDate())
                .quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice())
                .createdAt(orderDetail.getCreatedAt())
                .createdBy(orderDetail.getCreatedBy())
                .updatedAt(orderDetail.getUpdatedAt())
                .updatedBy(orderDetail.getUpdatedBy())
                .build();

        return response;
    }


}
