package com.ecommerce.demo.services;

import com.ecommerce.demo.Entities.*;
import com.ecommerce.demo.Exceptions.OrderNotFoundException;
import com.ecommerce.demo.Exceptions.ProductNotFoundException;
import com.ecommerce.demo.Exceptions.UserNotFoundException;
import com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos.PaymentLink;
import com.ecommerce.demo.repositories.CustomerOrderRepository;
import com.ecommerce.demo.repositories.InvoiceRepository;
import com.ecommerce.demo.dtos.orderdto.OrderCreateRequestDto;
import com.ecommerce.demo.services.productservice.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private CustomerOrderRepository customerOrderRepository;
    private InvoiceRepository invoiceRepository;
    private UserService userService;
    private ProductService productService;
    public OrderService(CustomerOrderRepository customerOrderRepository,
                        UserService userService,
                        ProductService productService,
                        InvoiceRepository invoiceRepository){
        this.customerOrderRepository = customerOrderRepository;
        this.userService = userService;
        this.productService = productService;
        this.invoiceRepository = invoiceRepository;
    }
    // Create Order
    public CustomerOrder createOrder(OrderCreateRequestDto request) throws UserNotFoundException, ProductNotFoundException, OrderNotFoundException {
        User user = userService.getUserById(request.getUserId());
        double totalPrice = 0;
        List<Product> products = new ArrayList<>();
        for(Long id:request.getProductIds()){
            Product product = productService.getProductDetails(id);
            products.add(product);
            totalPrice+=product.getPrice();
        }
        Invoice invoice = new Invoice();
        invoice.setProducts(products);
        invoice.setTotalAmount(totalPrice);
        invoice = invoiceRepository.save(invoice);

        CustomerOrder order = new CustomerOrder();
        order.setUser(user);
        order.setInvoice(invoice);
        order.setOrderStatus("PENDING");
        order = customerOrderRepository.save(order);
        return order;
    }

    // Get Order by ID
    public CustomerOrder getOrderById(Long id) throws OrderNotFoundException {
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return order;
    }

    // Update Order Status
    public CustomerOrder updateOrderStatus(Long id, String status) throws OrderNotFoundException {
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setOrderStatus(status);
        order = customerOrderRepository.save(order);
        return order;
    }

    // Delete Order
    public void deleteOrder(Long id) throws OrderNotFoundException {
        if (!customerOrderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found");
        }
        customerOrderRepository.deleteById(id);
    }

    public CustomerOrder updatePaymentStatusForOrder(Long id, String paymentLinkId, String status) throws OrderNotFoundException {
        CustomerOrder customerOrder = getOrderById(id);
        PaymentLink paymentLink = customerOrder.getPaymentLink();
        paymentLink.setStatus(status);
        customerOrder.setPaymentLink(paymentLink);
        customerOrder.setOrderStatus(status);
        customerOrder = customerOrderRepository.save(customerOrder);
        return customerOrder;
    }
    public List<CustomerOrder> getAllOrders(Pageable pageable){
        Page<CustomerOrder> customerOrders = customerOrderRepository.findAll(pageable);
        return customerOrders.getContent();
    }

    public List<CustomerOrder> getAllOrdersForUser(Pageable pageable,Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        Page<CustomerOrder> customerOrders = customerOrderRepository.findAllByUserId(user.getId(),pageable);
        return customerOrders.getContent();
    }
    public CustomerOrder updateOrder(CustomerOrder order){
        order = customerOrderRepository.save(order);
        return order;
    }
    public boolean isPaymentLinkAlreadyCreated(Long id) throws OrderNotFoundException {
        CustomerOrder order = getOrderById(id);
        PaymentLink paymentLink = order.getPaymentLink();
        return paymentLink==null?false:true;
    }
    public boolean isRefundAlreadyCreated(Long id) throws OrderNotFoundException {
        CustomerOrder order = getOrderById(id);
        Refund refund = order.getRefund();
        return refund==null?false:true;
    }
}
