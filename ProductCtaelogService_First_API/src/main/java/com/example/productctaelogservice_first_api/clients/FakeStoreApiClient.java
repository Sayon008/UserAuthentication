package com.example.productctaelogservice_first_api.clients;

import com.example.productctaelogservice_first_api.dtos.FakeStoreProductDTO;
import com.example.productctaelogservice_first_api.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDTO getProductById(Long productId) {

        ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTOResponseEntity =
                requestForEntity("https://fakestoreapi.com/products/{productId}", HttpMethod.GET,null, FakeStoreProductDTO.class, productId);

        return validateFakeStoreResponse(fakeStoreProductDTOResponseEntity);
    }


    public List<FakeStoreProductDTO> getAllProducts() {
//        Resultant list to store the json objects from the API
//        List<Product> products = new ArrayList<>();

//        RestTemplate restTemplate = restTemplateBuilder.build();

//        Figure-out the API from FakeStore to get all the products
//        Here the List<FakeStoreProductDTO>.class is a generic type, the generics is identified during insertions only -> currently its is same as like List<Integers> or List<Strings>
//        could be anything  ---> Here we need to give a concrete type, like what type is the FakeStoreProductDTO
//        In generics the datatype doesn't get realised as soon as we are using them
//        So here we cannot use List<FakeStoreProductDTO>.class

//        ResponseEntity<List<FakeStoreProductDTO>> listOfResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products", List<FakeStoreProductDTO>.class);

//         If we cannot use List then we can use simple primitive type like - array []

        ResponseEntity<FakeStoreProductDTO[]> listOfResponseEntity = requestForEntity("https://fakestoreapi.com/products",HttpMethod.GET,null,
                FakeStoreProductDTO[].class);

            FakeStoreProductDTO[] fakeStoreProductArray = validateFakeStoreResponse(listOfResponseEntity);

            if(fakeStoreProductArray != null) {
                List<FakeStoreProductDTO> fakeStoreProductDTOList = new ArrayList<>();
                for (FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProductArray) {
                    fakeStoreProductDTOList.add(fakeStoreProductDTO);
                }
                return fakeStoreProductDTOList;
            }
            return null;
    }



    //    Create Product
    public FakeStoreProductDTO createProduct(FakeStoreProductDTO fakeStoreProductDTO) {

        ResponseEntity<FakeStoreProductDTO> responseEntityCreateProduct =
                requestForEntity("https://fakestoreapi.com/products",HttpMethod.POST,fakeStoreProductDTO, FakeStoreProductDTO.class);

        return validateFakeStoreResponse(responseEntityCreateProduct);
    }



    public FakeStoreProductDTO replaceProduct(Long ProductId, FakeStoreProductDTO requestFakeStoreProductDTO) {

//        We dont have putForEntity method directly in the RestTemplate class, but we do have the postForEntity method
//        We will be overriding the postForEntity method and do some changes and make it act like as the postForEntity method

        ResponseEntity<FakeStoreProductDTO> response = requestForEntity("https://fakestoreapi.com/products/{productId}", HttpMethod.PUT, requestFakeStoreProductDTO, FakeStoreProductDTO.class, ProductId);

        return validateFakeStoreResponse(response);
    }


    // Overriding the postForEntity method and do some changes and make it act like as the postForEntity method
//    Converting to a generic method so that we can use this for any type of HTTP Request(PUT, POST, DELETE, GET, ... etc...)
    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {

        RestTemplate restTemplate = restTemplateBuilder.build();

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    private <T> T validateFakeStoreResponse(ResponseEntity<T> fakeStoreProductDTOResponseEntity){
        if(fakeStoreProductDTOResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDTOResponseEntity.getBody() != null){
            return fakeStoreProductDTOResponseEntity.getBody();
        }
        return null;
    }


}
