package com.admin.springbootadmin.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {
    BANK_TRANSFER(0,"계좌이체","계좌 이체 방식"),
    CARD(1,"신용카드","신용 카드 결제 방식"),
    CHECK_CARD(2,"체크카드","체크 카드 결제 방식");

    private Integer id;
    private String title;
    private String content;

}
