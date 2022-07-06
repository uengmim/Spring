package com.admin.springbootadmin.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {

    ORDERING(1,"주문 중","주문이 진행 중입니다."),
    COMPLETE(2,"주문 완료","주문이 완료 되었습니다."),
    CONFIRM(3,"결제 완료","결제가 완료 되었습니다.");

    private Integer id;
    private String title;
    private String content;

}
