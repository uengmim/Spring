package com.admin.springbootadmin.service;

import com.admin.springbootadmin.ifs.CRUDInterface;
import com.admin.springbootadmin.model.entity.OrderGroup;
import com.admin.springbootadmin.model.entity.User;
import com.admin.springbootadmin.model.enumClass.UserStatus;
import com.admin.springbootadmin.model.network.Header;
import com.admin.springbootadmin.model.network.Pagination;
import com.admin.springbootadmin.model.network.request.UserApiRequest;
import com.admin.springbootadmin.model.network.response.ItemApiResponse;
import com.admin.springbootadmin.model.network.response.OrderGroupApiResponse;
import com.admin.springbootadmin.model.network.response.UserApiResponse;
import com.admin.springbootadmin.model.network.response.UserOrderInfoApiResponse;
import com.admin.springbootadmin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiService extends BaseService<UserApiRequest, UserApiResponse, User> {


    @Autowired
    private OrderGroupApiService orderGroupApiService;

    @Autowired
    private ItemApiService itemApiService;

    //1. request data
    //2. user crud
    //3. response data
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        UserApiRequest userApiRequest = request.getData();

        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .email(userApiRequest.getEmail())
                .phoneNumber(userApiRequest.getPhoneNumber())
                .status(UserStatus.REGISTERED)
                .registeredAt(LocalDateTime.now())
                .build();

        User savedUser = baseRespository.save(user);

       return Header.OK(response(savedUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        Optional<User> optional = baseRespository.findById(id);

        return optional
                .map(user -> Header.OK(response(user)))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();

        Optional<User> optional = baseRespository.findById(userApiRequest.getId());

        return optional.map(user -> {
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());


            return user;
        })
        .map(user ->  baseRespository.save(user)) // update
        .map(updatedUser ->  Header.OK(response(updatedUser))) // response
        .orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    @Override
    public Header<List<UserApiResponse>> search(@PageableDefault(sort = "id",direction = Sort.Direction.ASC, size = 15) Pageable pageable) {
        Page<User> users = baseRespository.findAll(pageable);

        List<UserApiResponse> userApiResponseList = users.stream()
                .map(user -> response(user))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .currentPage(users.getNumber())
                .currentElements(users.getNumberOfElements())
                .build();

        return Header.OK(userApiResponseList, pagination);
    }

    @Override
    public Header delete(Long id) {
        Optional<User> optional = baseRespository.findById(id);


        return optional.map(user -> {
            baseRespository.delete(user);
            return Header.OK();
        })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }


    private UserApiResponse response(User user) {
        UserApiResponse response = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();


        return response;
    }


    public Header<UserOrderInfoApiResponse> orderInfo(Long id){

        //User
        User user = baseRespository.getOne(id);
        UserApiResponse userApiResponse = response(user);



        //OrderGroup
        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
                .map(orderGroup -> {
                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiService.response(orderGroup);
                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                            .map(detail -> detail.getItem())
                            .map(item -> itemApiService.response(item))
                            .collect(Collectors.toList());

                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);

                    return orderGroupApiResponse;
                })
                .collect(Collectors.toList());

        //Item

        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();
        return Header.OK(userOrderInfoApiResponse);
    }
}
