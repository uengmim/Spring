package com.admin.springbootadmin.controller.api;

import com.admin.springbootadmin.controller.CRUDController;
import com.admin.springbootadmin.model.entity.Category;
import com.admin.springbootadmin.model.network.request.CategoryApiRequest;
import com.admin.springbootadmin.model.network.response.CategoryApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryApiController extends CRUDController<CategoryApiRequest, CategoryApiResponse, Category> {

}
