package com.ecommerce.demo.PaymentGateways;
import com.ecommerce.demo.Entities.User;
import com.ecommerce.demo.Exceptions.PaymentNotCompletedException;
import com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos.PaymentLink;
import com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos.RazorpayPaymentLink;
import com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos.RazorpayRefundDto;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RazopayPaymentGateway implements PaymentGateway{

    @Autowired
    RazorpayClient razorpayClient;
    @Override
    public RazorpayPaymentLink generatePaymentLink(double amount, String currency,Long orderId,
                                      Map<String,String> notes, String description) {

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",((int)amount)*100);
        paymentLinkRequest.put("expire_by",Instant.now().getEpochSecond()+(30*60));
        paymentLinkRequest.put("reference_id",Long.toString(orderId));
        paymentLinkRequest.put("description",description);
        paymentLinkRequest.put("reminder_enable",true);
        paymentLinkRequest.put("notes",new JSONObject(notes));
        paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method","get");
        com.razorpay.PaymentLink payment = null;
        try {
            payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        } catch (RazorpayException e) {
//            log.error("Failed to create a Payment link {}", e.getMessage());
            return null;
        }
        return new RazorpayPaymentLink(payment.get("id"),payment.get("short_url"),payment.get("status"));
    }

    @Override
    public RazorpayPaymentLink getPaymentLink(String id) {
        com.razorpay.PaymentLink paymentLink = null;
        try {
            paymentLink = razorpayClient.paymentLink.fetch(id);
        }
        catch (Exception e){
//            log.error("Failed to get a Payment link {}", e.getMessage());
            return null;
        }
        return new RazorpayPaymentLink(paymentLink.get("id"),paymentLink.get("short_url"),paymentLink.get("status"));
    }

    @Override
    public String refundForPaymentLink(String id) throws PaymentNotCompletedException {
        String refundId = null;
        try {
            com.razorpay.PaymentLink paymentLink = razorpayClient.paymentLink.fetch(id);
            if(!paymentLink.get("status").equals("paid")){
                throw new PaymentNotCompletedException("Payment "+paymentLink.get("id")+" not completed to refund");
            }
            JSONArray payments = paymentLink.get("payments");
            JSONObject payment = (JSONObject) payments.get(0);
            String paymentId = payment.getString("payment_id");
            Refund refund = razorpayClient.payments.refund(paymentId);
            refundId = refund.get("id");
        }
        catch (RazorpayException razorpayException){
//            log.error("Failed to create a refund {}", razorpayException.getMessage());
        }
        return refundId;
    }

    public List<RazorpayPaymentLink> getPaymentLinksForReferenceId(String id) {
        JSONObject params = new JSONObject();
        params.put("reference_id",id);
        List<com.razorpay.PaymentLink>  paymentLinks = new ArrayList<>();
        try{
        paymentLinks = razorpayClient.paymentLink.fetchAll(params);
        }catch (RazorpayException razorpayException){
            //log.error("Failed to get PaymentLinks for OrderId {} {}", id, razorpayException.getMessage());
            return null;
        }
        List<RazorpayPaymentLink> razorpayPaymentLinks = new ArrayList<>();
        for(com.razorpay.PaymentLink paymentLink:paymentLinks){
            razorpayPaymentLinks.add(new RazorpayPaymentLink(paymentLink.get("id"),
                    paymentLink.get("short_url"),
                    paymentLink.get("status")));
        }
        return razorpayPaymentLinks;
    }


    public RazorpayRefundDto getRefund(String id) {
        RazorpayRefundDto razorpayRefundDto = null;
        try {
            com.razorpay.Refund refund = razorpayClient.refunds.fetch(id);
            razorpayRefundDto = new RazorpayRefundDto(refund.get("id"), refund.get("status"));
        }
        catch (RazorpayException razorpayException){
            //log.error("failed to get refund details");
        }
        return razorpayRefundDto;
    }

}
