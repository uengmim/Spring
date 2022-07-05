package com.admin.springbootadmin.controller;


import com.admin.springbootadmin.model.network.Header;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class GetController {

    @GetMapping("/getHeader")
    public Header getHeader() {
            // {"resultCode" : "OK" , "}
            return Header.builder().resultCode("OK").description("OK").build();
    }
}
