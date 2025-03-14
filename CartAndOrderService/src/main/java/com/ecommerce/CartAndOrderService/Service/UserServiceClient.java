package com.ecommerce.CartAndOrderService.Service;

import com.ecommerce.CartAndOrderService.Dto.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class UserServiceClient {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public UserServiceClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Address getAddressByUserIdAndAddressId(Long userId, Long addressId, String token) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/users/"+ userId+"/address/"+addressId)
                .header("Authorization","Bearer "+ token)
                .retrieve()
                .bodyToMono(Address.class)
                .block();
    }


}
