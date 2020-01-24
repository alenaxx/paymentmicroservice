package payment.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "order-client")
public interface OrderServiceFeignClient {
    @PutMapping("/orders/{id}/status/{status}")
    ResponseEntity setOrderStatus(@PathVariable("id") UUID id, @PathVariable("status") String status);
}
