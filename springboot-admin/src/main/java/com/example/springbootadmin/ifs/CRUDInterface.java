package com.admin.springbootadmin.ifs;

import com.admin.springbootadmin.model.network.Header;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CRUDInterface<Req, Res> {

    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header<List<Res>> search(Pageable pageable);

    Header delete(Long id);
}
