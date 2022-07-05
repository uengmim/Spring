package com.admin.springbootadmin.controller.api;

import com.admin.springbootadmin.controller.CRUDController;
import com.admin.springbootadmin.model.entity.User;
import com.admin.springbootadmin.model.network.Header;
import com.admin.springbootadmin.model.network.request.UserApiRequest;
import com.admin.springbootadmin.model.network.response.UserApiResponse;
import com.admin.springbootadmin.model.network.response.UserOrderInfoApiResponse;
import com.admin.springbootadmin.repository.UserRepository;
import com.admin.springbootadmin.service.UserApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController extends CRUDController<UserApiRequest, UserApiResponse, User> {

    @Autowired
    private UserApiService userApiService;

    @GetMapping("/{id}/orderInfo")
    public Header<UserOrderInfoApiResponse> orderInfo(@PathVariable Long id) {
        return userApiService.orderInfo(id);
    }

}
