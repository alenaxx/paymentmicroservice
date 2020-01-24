package payment.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import payment.dto.PaymentDto;
import payment.dto.UserDetailsDto;
import payment.model.Payment;

import java.util.UUID;


@Repository("postgres")
public class PaymentDaoService implements PaymentDao {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public PaymentDaoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PaymentDto initPayment(UUID id, UUID orderId, UserDetailsDto userDetails) {
        Payment payment = new Payment(
                UUID.randomUUID(),
                orderId,
                userDetails.getCardInfo(),
                userDetails.getName()
        );

        jdbcTemplate.update(
                "INSERT INTO payment ( paymentId,orderId,status,username) VALUES (?,?, ?, ? )",
                payment.getPaymentId(), payment.getOrderId(), payment.getStatus(), payment.getUsername()
        );
        return PaymentDto.fromPayment(payment);
    }


    @Override
    public PaymentDto getPaymentStatus(UUID orderId) {
        String sql = "SELECT * FROM payment WHERE orderId = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{orderId}, (resultSet, i) -> {
            UUID orderId1 = UUID.fromString(resultSet.getString("orderId"));
            String status = resultSet.getString("status");
            return new PaymentDto(orderId1, status);
        });
    }
}
