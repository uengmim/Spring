package com.admin.springbootadmin.service;

import com.admin.springbootadmin.model.entity.Category;
import com.admin.springbootadmin.model.network.Header;
import com.admin.springbootadmin.model.network.request.CategoryApiRequest;
import com.admin.springbootadmin.model.network.response.CategoryApiResponse;
import com.admin.springbootadmin.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryApiService extends BaseService<CategoryApiRequest, CategoryApiResponse, Category> {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {
        CategoryApiRequest body = request.getData();

        Category category = Category.builder()
                .title(body.getTitle())
                .type(body.getType())
                .createdAt(LocalDateTime.now())
                .createdBy(body.getCreatedBy())
                .build();

        Category savedCategoryType = baseRespository.save(category);

        return Header.OK(response(savedCategoryType));
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return baseRespository.findById(id)
                .map(categoryType -> Header.OK(response(categoryType)))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {
        CategoryApiRequest categoryApiRequest = request.getData();

        return baseRespository.findById(categoryApiRequest.getId())
                .map(categoryType -> {
                    categoryType
                            .setType(categoryApiRequest.getType())
                            .setTitle(categoryApiRequest.getTitle())
                            .setCreatedAt(categoryApiRequest.getCreatedAt())
                            .setCreatedBy(categoryApiRequest.getCreatedBy())
                            .setUpdatedAt(categoryApiRequest.getUpdatedAt())
                            .setUpdatedBy(categoryApiRequest.getUpdatedBy());

                    Category newCategoryType = baseRespository.save(categoryType);
                    return newCategoryType;
                })
                .map(cateogry -> Header.OK(response(cateogry)))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<List<CategoryApiResponse>> search(Pageable pageable) {
        Page<Category> categorys = baseRespository.findAll(pageable);

        List<CategoryApiResponse> categoryApiResponseList = categorys.stream()
                .map(category -> response(category))
                .collect(Collectors.toList());

        return Header.OK(categoryApiResponseList);
    }

    @Override
    public Header delete(Long id) {
        return baseRespository.findById(id)
                .map(categoryType -> {
                    baseRespository.delete(categoryType);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private CategoryApiResponse response(Category categoryType) {
        CategoryApiResponse response = CategoryApiResponse
                .builder()
                .id(categoryType.getId())
                .type(categoryType.getType())
                .title(categoryType.getTitle())
                .createdAt(categoryType.getCreatedAt())
                .createdBy(categoryType.getCreatedBy())
                .updatedAt(categoryType.getUpdatedAt())
                .updatedBy(categoryType.getUpdatedBy())
                .build();

        return response;
    }

}
