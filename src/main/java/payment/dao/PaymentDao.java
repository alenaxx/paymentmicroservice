package payment.dao;


import payment.dto.PaymentDto;
import payment.dto.UserDetailsDto;

import java.util.UUID;

public interface PaymentDao {

    PaymentDto initPayment(UUID id, UUID orderId, UserDetailsDto userDetails);

    default PaymentDto initPayment(UUID orderId, UserDetailsDto userDetails) {
        UUID id = UUID.randomUUID();
        return initPayment(id, orderId, userDetails);
    }

    PaymentDto getPaymentStatus(UUID orderId);


}
