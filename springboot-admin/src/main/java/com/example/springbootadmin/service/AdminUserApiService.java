package com.admin.springbootadmin.service;

import com.admin.springbootadmin.ifs.CRUDInterface;
import com.admin.springbootadmin.model.entity.AdminUser;
import com.admin.springbootadmin.model.enumClass.AdminUserRole;
import com.admin.springbootadmin.model.enumClass.UserStatus;
import com.admin.springbootadmin.model.network.Header;
import com.admin.springbootadmin.model.network.request.AdminUserApiRequest;
import com.admin.springbootadmin.model.network.response.AdminUserApiResponse;
import com.admin.springbootadmin.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminUserApiService extends BaseService<AdminUserApiRequest, AdminUserApiResponse, AdminUser> {

    @Override
    public Header<AdminUserApiResponse> create(Header<AdminUserApiRequest> request) {

        AdminUserApiRequest adminUserApiRequest = request.getData();

        AdminUser adminUser = AdminUser.builder()
                .account(adminUserApiRequest.getAccount())
                .password(adminUserApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .role(AdminUserRole.SUPER)
                .loginFailCount(0)
                .registeredAt(LocalDateTime.now())
                .build();

        AdminUser savedAdminUser = baseRespository.save(adminUser);

        return Header.OK(response(savedAdminUser));
    }

    @Override
    public Header<AdminUserApiResponse> read(Long id) {
        Optional<AdminUser> optional = baseRespository.findById(id);
        return optional
                .map(adminUser -> Header.OK(response(adminUser)))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest adminUserApiRequest = request.getData();

        Optional<AdminUser> optional = baseRespository.findById(adminUserApiRequest.getId());
        return optional
                .map(adminUser -> {
                    adminUser
                            .setAccount(adminUserApiRequest.getAccount())
                            .setPassword(adminUserApiRequest.getPassword())
                            .setStatus(adminUserApiRequest.getStatus())
                            .setRole(adminUserApiRequest.getRole())
                            .setLastLoginAt(adminUserApiRequest.getLastLoginAt())
                            .setPasswordUpdatedAt(adminUserApiRequest.getPasswordUpdatedAt())
                            .setLoginFailCount(adminUserApiRequest.getLoginFailCount())
                            .setRegisteredAt(adminUserApiRequest.getRegisteredAt())
                            .setUnregisteredAt(adminUserApiRequest.getUnregisteredAt());

                    return adminUser;
                })
                .map(adminUser -> baseRespository.save(adminUser))
                .map(updatedAdminUser -> Header.OK(response(updatedAdminUser)))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<List<AdminUserApiResponse>> search(Pageable pageable) {
        Page<AdminUser> adminUsers = baseRespository.findAll(pageable);

        List<AdminUserApiResponse> adminUserApiResponseList = adminUsers.stream()
                .map(adminUser -> response(adminUser))
                .collect(Collectors.toList());

        return Header.OK(adminUserApiResponseList);
    }

    @Override
    public Header delete(Long id) {
        Optional<AdminUser> optional = baseRespository.findById(id);

        return optional
                .map(adminUser -> {
                    baseRespository.delete(adminUser);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private AdminUserApiResponse response(AdminUser adminUser) {
        AdminUserApiResponse response = AdminUserApiResponse.builder()
                .id(adminUser.getId())
                .account(adminUser.getAccount())
                .password(adminUser.getPassword())
                .status(adminUser.getStatus())
                .role(adminUser.getRole())
                .lastLoginAt(adminUser.getLastLoginAt())
                .passwordUpdatedAt(adminUser.getPasswordUpdatedAt())
                .loginFailCount(adminUser.getLoginFailCount())
                .registeredAt(adminUser.getRegisteredAt())
                .unregisteredAt(adminUser.getUnregisteredAt())
                .build();

        return response;
    }
}
