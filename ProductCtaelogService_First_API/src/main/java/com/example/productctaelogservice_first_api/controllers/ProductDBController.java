//package com.example.productctaelogservice_first_api.controllers;
//
//import com.example.productctaelogservice_first_api.dtos.CategoryDTO;
//import com.example.productctaelogservice_first_api.dtos.ProductDTO;
//import com.example.productctaelogservice_first_api.models.Category;
//import com.example.productctaelogservice_first_api.models.Product;
//import com.example.productctaelogservice_first_api.services.IProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/products")
//public class ProductDBController {
//
//        @Autowired
//        @Qualifier("sps")
//        private IProductService productService;
//
//        @GetMapping
//        public List<ProductDTO> getAllProducts(){
//            List<ProductDTO> productDTOList = new ArrayList<>();
//
//            List<Product> products = productService.getAllProducts();
//
//            for (Product product : products) {
//                productDTOList.add(from(product));
//            }
//
//            return productDTOList;
//        }
//
//        @GetMapping("{productId}")
//        public ResponseEntity<ProductDTO> findProductById(@PathVariable("productId") Long id){
//            try {
////        Insert Headers -> for proper context
//                MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//
//                if (id <= 0) {
//                    headers.add("Id value cannot be less than", "0");
////                return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
////                Return exception instead 404 Not Found Status
//                    throw  new IllegalArgumentException("Please try with productId > 0");
//                }
//
//                Product product = productService.getProductById(id);
//
//                headers.add("Called By", "Intelligent");
//
//                if (product == null) return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
//
//                return new ResponseEntity<>(from(product), headers, HttpStatus.OK);
//            }
//            catch (IllegalArgumentException exception){
//                throw exception;
//            }
//        }
//
//
//        @PostMapping
//        public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO){
//
//            Product product = from(productDTO);
//
//            Product addProduct = productService.saveProduct(product);
//
//            if(addProduct == null){
//                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//            }
//
//            return new ResponseEntity<>(from(addProduct), HttpStatus.CREATED);
//
//        }
//
//        //    Edit a Product Or Update a Product - We use PUT Request
//        @PutMapping("/{id}")
//        public ProductDTO replaceProduct(@PathVariable Long id, @RequestBody ProductDTO requestProduct){
//
//            Product productRequest = from(requestProduct);
//
//            Product product = productService.replaceProduct(id,productRequest);
//
//            return from(product);
//        }
//
//        //    Converting Input = Product to Output = ProductDTO object
//        private ProductDTO from(Product product) {
//            ProductDTO productDTO = new ProductDTO();
//            productDTO.setId(product.getId());
//            productDTO.setName(product.getName());
//            productDTO.setDescription(product.getDescription());
//            productDTO.setPrice(product.getPrice());
//            productDTO.setImageUrl(product.getImageUrl());
//
//            if(product.getCategory() != null) {
//                CategoryDTO categoryDTO = new CategoryDTO();
//                categoryDTO.setId(product.getCategory().getId());
//                categoryDTO.setName(product.getCategory().getName());
//                categoryDTO.setDescription(product.getCategory().getDescription());
//
//                productDTO.setCategory(categoryDTO);
//            }
//
//            return productDTO;
//        }
//
//        //    Converting Input = Product to output = ProductDTO object
//        private Product from(ProductDTO productDTO) {
//            Product product = new Product();
//            product.setId(productDTO.getId());
//            product.setName(productDTO.getName());
//            product.setDescription(productDTO.getDescription());
//            product.setPrice(productDTO.getPrice());
//            product.setImageUrl(productDTO.getImageUrl());
//
//            if(productDTO.getCategory() != null) {
//                Category category = new Category();
//                category.setId(productDTO.getCategory().getId());
//                category.setName(productDTO.getCategory().getName());
//                category.setDescription(productDTO.getCategory().getDescription());
//
//                product.setCategory(category);
//            }
//
//            return product;
//        }
//}
