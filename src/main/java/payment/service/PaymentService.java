package payment.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import payment.dao.PaymentDao;
import payment.dto.PaymentDto;
import payment.dto.UserDetailsDto;
import payment.feignClient.OrderServiceFeignClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentDao paymentDao;

    @Autowired
    private OrderServiceFeignClient orderServiceFeignClient;

    @Autowired
    public PaymentService(@Qualifier("postgres") PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public PaymentDto initPayment(UUID orderId, UserDetailsDto userDetails) {
        this.changeOrderStatus(orderId, userDetails);
        return paymentDao.initPayment(orderId, userDetails);
    }


    public PaymentDto getPaymentStatus(UUID orderId) {
        return paymentDao.getPaymentStatus(orderId);
    }

    private void changeOrderStatus(UUID orderId, UserDetailsDto userDetailsDto) {
        String orderStatus = "";
        if (userDetailsDto.getCardInfo().equals("AUTHORIZED")) {
            orderStatus = "PAID";
        } else if (userDetailsDto.getCardInfo().equals("UNAUTHORIZED")) {
            orderStatus = "FAILED";
        }

        if (!orderStatus.equals("")) {
          /* URL url = null;
            try {
                url = new URL(String.format("http://localhost:8011/orders/%s/status/%s", orderId, orderStatus));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("PUT");
                con.setDoOutput(true);
                con.setDoInput(true);
                int responseCode = con.getResponseCode();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpURLConnection con = null;
          */
          orderServiceFeignClient.setOrderStatus(orderId, orderStatus);

        }
    }


}
