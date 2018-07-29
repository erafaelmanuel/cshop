package com.rem.cs.rest.client.resource.client;

import com.rem.cs.rest.client.resource.BaseService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpMethod.GET;

@Service("categoryRestClientService")
public class CategoryService extends BaseService {

    public PagedResources<Category> findAll(String search, int page, int size, String sort) {
        final String url = "http://localhost:8080/api/categories";
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (!StringUtils.isEmpty(search)) {
            builder.queryParam("search", search);
        }
        if (page > 0) {
            builder.queryParam("page", page);
        }
        if (size > 0) {
            builder.queryParam("size", size);
        }
        if (!StringUtils.isEmpty(sort)) {
            builder.queryParam("sort", sort);
        }
        return restTemplate.exchange(builder.toUriString(), GET, null,
                new ParameterizedTypeReference<PagedResources<Category>>() {}).getBody();
    }

    public Resource<Category> findById(String id) {
        final String url = "http://localhost:8080/api/categories/";
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (!StringUtils.isEmpty(id)) {
            builder.pathSegment(id);
        }
        return restTemplate.exchange(builder.toUriString(), GET, null,
                new ParameterizedTypeReference<Resource<Category>>() {}).getBody();
    }

    public PagedResources<Category> findByParentIsNull(int page, int size, String sort) {
        final String url = "http://localhost:8080/api/categories/search/findByParentIsNull";
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (page > 0) {
            builder.queryParam("page", page);
        }
        if (size > 0) {
            builder.queryParam("size", size);
        }
        if (!StringUtils.isEmpty(sort)) {
            builder.queryParam("sort", sort);
        }
        return restTemplate.exchange(builder.toUriString(), GET, null,
                new ParameterizedTypeReference<PagedResources<Category>>() {}).getBody();
    }

    public PagedResources<Category> findByParentId(String categoryId, int page, int size, String sort) {
        final String url = "http://localhost:8080/api/categories/search/findByParentId";
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (!StringUtils.isEmpty(categoryId)) {
            builder.queryParam("categoryId", categoryId);
        }
        if (page > 0) {
            builder.queryParam("page", page);
        }
        if (size > 0) {
            builder.queryParam("size", size);
        }
        if (!StringUtils.isEmpty(sort)) {
            builder.queryParam("sort", sort);
        }
        return restTemplate.exchange(builder.toUriString(), GET, null,
                new ParameterizedTypeReference<PagedResources<Category>>() {}).getBody();
    }

    public Resources<Category> findByAncestor(String categoryId, int page, int size, String sort) {
        final String url = "http://localhost:8080/api/categories/search/findByAncestor";
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (!StringUtils.isEmpty(categoryId)) {
            builder.queryParam("categoryId", categoryId);
        }
        return restTemplate.exchange(builder.toUriString(), GET, null,
                new ParameterizedTypeReference<Resources<Category>>() {}).getBody();
    }
}
