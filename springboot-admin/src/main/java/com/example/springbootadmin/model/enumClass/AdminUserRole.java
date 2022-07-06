package com.admin.springbootadmin.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AdminUserRole {

    SUPER(0, "슈퍼 유저","모든 권한 사용 가능");

    private Integer id;
    private String title;
    private String description;

}
