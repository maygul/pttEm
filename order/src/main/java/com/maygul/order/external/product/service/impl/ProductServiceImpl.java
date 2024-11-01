package com.maygul.order.external.product.service.impl;

import com.maygul.order.exception.ProductServiceCallException;
import com.maygul.order.external.product.service.ProductService;
import com.maygul.order.external.product.service.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT_SERVICE_BASE_URL = "http://product-service/";
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final RestTemplate restTemplate;

    public ProductServiceImpl(
            @Qualifier("loggedRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ProductDto> getAllWithIdList(Set<Long> longs) {
        try {
             Map<String,List> request = Map.of("ids", longs.stream().collect(Collectors.toList()));
             HttpEntity entity = new HttpEntity(request);
            var responseEntity = restTemplate.exchange(String.format("%s/products/provide-product-info-for-order", PRODUCT_SERVICE_BASE_URL), HttpMethod.GET, entity, new ParameterizedTypeReference<List<ProductDto>>() {
            });
            var responseBody = responseEntity.getBody();
            return responseBody;
        } catch (HttpStatusCodeException ex) {
            log.error("Error while getting products from product service", ex);
            throw new ProductServiceCallException();
        }
    }

    @Override
    public Boolean reserveProducts(Map<Long, Integer> productsWithCounts) {
        var uri = String.format("%s/products/reserve-products", PRODUCT_SERVICE_BASE_URL);
        var method = HttpMethod.POST;
        Map<String,Map> data = Map.of("productAndCountMap", productsWithCounts);
        var httpEntity = new HttpEntity<>(data);
        var responseType = Void.class;
        try {
            var responseEntity = restTemplate.exchange(uri, method, httpEntity, responseType);
            return responseEntity.getStatusCode().is2xxSuccessful();
        } catch (HttpStatusCodeException ex) {
            log.error("Error while reserving products from product service", ex);
            throw new ProductServiceCallException();
        }
    }

    @Override
    public Boolean putReservedProductsBack(Map<Long, Integer> negativeDiff) {
        var uri = String.format("%s/products/put-reserved-products-back", PRODUCT_SERVICE_BASE_URL);
        var method = HttpMethod.POST;
        Map<String,Map> data = Map.of("productAndCountMap", negativeDiff);
        var httpEntity = new HttpEntity<>(data);
        var responseType = Void.class;

        try {
            var responseEntity = restTemplate.exchange(uri, method, httpEntity, responseType);
            return responseEntity.getStatusCode().is2xxSuccessful();
        } catch (HttpStatusCodeException ex) {
            log.error("Error while putting reserved products back from product service", ex);
            throw new ProductServiceCallException();
        }
    }

}
