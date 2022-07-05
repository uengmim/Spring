package com.admin.springbootadmin.controller;

import com.admin.springbootadmin.ifs.CRUDInterface;
import com.admin.springbootadmin.model.network.Header;
import com.admin.springbootadmin.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Component
public abstract class CRUDController<Req,Res,Entity> implements CRUDInterface<Req,Res> {

    @Autowired(required = false)
    protected BaseService<Req,Res,Entity> baseService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        log.info("CREATE {}", request);
        return baseService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable Long id) {
        log.info("READ {}", id);
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> request) {
        log.info("UPDATE {}", request);
        return baseService.update(request);
    }

    @Override
    @GetMapping("")
    public Header<List<Res>> search(Pageable pageable) {
        log.info("SEARCH {}", pageable);
        return baseService.search(pageable);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        log.info("DELETE {}", id);
        return baseService.delete(id);
    }


}
