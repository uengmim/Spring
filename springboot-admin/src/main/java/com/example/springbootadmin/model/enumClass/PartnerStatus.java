package com.admin.springbootadmin.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PartnerStatus {

    REGISTERED(0,"등록","등록이 완료 되었습니다."),
    UNREGISTERED(1,"미등록","등록되지 않았습니다.");

    private Integer id;
    private String title;
    private String description;

}
