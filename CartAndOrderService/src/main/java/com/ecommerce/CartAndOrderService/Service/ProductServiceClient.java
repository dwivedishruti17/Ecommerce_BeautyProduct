package com.ecommerce.CartAndOrderService.Service;

import com.ecommerce.CartAndOrderService.Dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceClient {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public ProductServiceClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public ProductDto getProductById(Long productId) {
        String url = "http://localhost:8080/products/" + productId;
        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

    public Double fetchProductprice(Long productId){
        String url = "http://localhost:8080/products/" + productId;
        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Double.class)
                .block();
    }
    public String fetchProductName(Long productId){
        String url = "http://localhost:8080/products/" + productId;
        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
