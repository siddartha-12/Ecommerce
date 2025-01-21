package com.ecommerce.demo.dtos.orderdto;

import com.ecommerce.demo.Entities.CustomerOrder;
import com.ecommerce.demo.Entities.Invoice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    private Long id;
    private String status;
    private Invoice invoice;
    public static OrderResponseDto createOrderResponseDto(CustomerOrder customerOrder){
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.status = customerOrder.getOrderStatus();
        orderResponseDto.id = customerOrder.getId();
        orderResponseDto.invoice = customerOrder.getInvoice();
        return orderResponseDto;
    }
}
