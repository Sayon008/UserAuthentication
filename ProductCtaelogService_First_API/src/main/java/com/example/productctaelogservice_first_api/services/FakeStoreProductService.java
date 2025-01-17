package com.example.productctaelogservice_first_api.services;

import com.example.productctaelogservice_first_api.clients.FakeStoreApiClient;
import com.example.productctaelogservice_first_api.dtos.FakeStoreProductDTO;
import com.example.productctaelogservice_first_api.models.Category;
import com.example.productctaelogservice_first_api.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fkps")
public class FakeStoreProductService implements IProductService{

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;

    public Product getProductById(Long productId) {
        FakeStoreProductDTO fakeStoreProductDTO = fakeStoreApiClient.getProductById(productId);

        if(fakeStoreProductDTO != null){
            return from(fakeStoreProductDTO);
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
//        Resultant list to store the json objects from the API
        List<FakeStoreProductDTO> fakeStoreProductDTOList = fakeStoreApiClient.getAllProducts();

        List<Product> products = new ArrayList<>();
        if(fakeStoreProductDTOList != null){
            for(FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProductDTOList){
                products.add(from(fakeStoreProductDTO));
            }

            return products;
        }

        return null;
    }

//    Create Product
    public Product createProduct(Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setTitle(product.getName());
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setImage(product.getImageUrl());
        fakeStoreProductDTO.setCategory(product.getCategory().getName());

        FakeStoreProductDTO fakeStoreProductDTOClientResponse = fakeStoreApiClient.createProduct(fakeStoreProductDTO);
        if(fakeStoreProductDTOClientResponse != null){
            return from(fakeStoreProductDTOClientResponse);
        }

        return null;
    }

    @Override
    public Product replaceProduct(Long productId, Product requestProduct) {

//        We dont have putForEntity method directly in the RestTemplate class, but we do have the postForEntity method
//        We will be overriding the postForEntity method and do some changes and make it act like as the postForEntity method

        // Convert the Product object to FakeStoreProductDTO
        FakeStoreProductDTO fakeStoreProductDTOrequest = from(requestProduct);

        // Call the client layer to handle the API request
        FakeStoreProductDTO updateFakeStoreProductDTORequest = fakeStoreApiClient.replaceProduct(productId,fakeStoreProductDTOrequest);

        // Convert the response DTO back to Product and return
        return from(updateFakeStoreProductDTORequest);
    }

    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    // Overriding the postForEntity method and do some changes and make it act like as the postForEntity method
//    Converting to a generic method so that we can use this for any type of HTTP Request(PUT, POST, DELETE, GET, ... etc...)
    public <T> ResponseEntity<T> requestForEntity(String url,HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {

        RestTemplate restTemplate = restTemplateBuilder.build();

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }


    //    Mapper function which takes input a FakeStoreProductDTO object and coverts it to Product object

    private Product from(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setName(fakeStoreProductDTO.getTitle());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setPrice(fakeStoreProductDTO.getPrice());
        product.setImageUrl(fakeStoreProductDTO.getImage());

        Category category = new Category();
        category.setName(fakeStoreProductDTO.getCategory());

        product.setCategory(category);

        return product;
    }



//    Mapper function which takes input a Product object and coverts it to FakeStoreProductDTO object

    private FakeStoreProductDTO from(Product product) {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setId(product.getId());
        fakeStoreProductDTO.setTitle(product.getName());
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setImage(product.getImageUrl());

        if(product.getCategory() != null){
            fakeStoreProductDTO.setCategory(product.getCategory().getName());
        }

        return fakeStoreProductDTO;
    }
}
