package com.admin.springbootadmin.controller.api;

import com.admin.springbootadmin.controller.CRUDController;
import com.admin.springbootadmin.model.entity.Partner;
import com.admin.springbootadmin.model.network.request.PartnerApiRequest;
import com.admin.springbootadmin.model.network.response.PartnerApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partner")
public class PartnerApiController extends CRUDController<PartnerApiRequest, PartnerApiResponse, Partner> {
}
