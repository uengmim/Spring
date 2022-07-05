package com.admin.springbootadmin.controller.api;

import com.admin.springbootadmin.controller.CRUDController;
import com.admin.springbootadmin.model.entity.Item;
import com.admin.springbootadmin.model.network.request.ItemApiRequest;
import com.admin.springbootadmin.model.network.response.ItemApiResponse;
import com.admin.springbootadmin.service.ItemApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping("/api/item")
public class ItemApiController extends CRUDController<ItemApiRequest, ItemApiResponse, Item> {


}
