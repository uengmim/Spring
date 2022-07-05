package com.admin.springbootadmin.controller.api;

import com.admin.springbootadmin.controller.CRUDController;
import com.admin.springbootadmin.model.entity.AdminUser;
import com.admin.springbootadmin.model.network.request.AdminUserApiRequest;
import com.admin.springbootadmin.model.network.response.AdminUserApiResponse;
import com.admin.springbootadmin.service.AdminUserApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping("/api/adminUser")
public class AdminUserApiController extends CRUDController<AdminUserApiRequest, AdminUserApiResponse, AdminUser> {

}
