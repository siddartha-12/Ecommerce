package com.ecommerce.demo.controllers;

import com.ecommerce.demo.Entities.CustomerOrder;
import com.ecommerce.demo.Entities.Refund;
import com.ecommerce.demo.Exceptions.*;
import com.ecommerce.demo.dtos.orderdto.OrderCreateRequestDto;
import com.ecommerce.demo.dtos.orderdto.OrderResponseDto;
import com.ecommerce.demo.dtos.paymentlinkdto.RazorpayDtos.PaymentLink;
import com.ecommerce.demo.services.OrderService;
import com.ecommerce.demo.services.PaymentService;
import com.ecommerce.demo.services.apilimiter.ApiLimiterService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private PaymentService paymentService;
    private ApiLimiterService apiLimiterService;
    public OrderController(OrderService orderService,PaymentService paymentService,ApiLimiterService apiLimiterService){
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.apiLimiterService = apiLimiterService;
    }

    // Create Order
    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderCreateRequestDto request) throws UserNotFoundException, ProductNotFoundException, OrderNotFoundException {
        CustomerOrder customerOrder = orderService.createOrder(request);
        return new ResponseEntity<>(OrderResponseDto.createOrderResponseDto(customerOrder),HttpStatus.CREATED);
    }

    // Get Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) throws OrderNotFoundException {
        sample();
        CustomerOrder customerOrder = orderService.getOrderById(id);
        return new ResponseEntity<>(OrderResponseDto.createOrderResponseDto(customerOrder),HttpStatus.OK);
    }
    // Get Order For User
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@RequestParam(required = false) Long userId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) throws UserNotFoundException, ApiLimiterException {
        int[] prices = {30,20,25};
        int m = 4;
        int n = prices.length;
        Queue<Integer> q= new PriorityQueue<>((a, b)->{
            return b-a;
        });
        for(Integer integer:prices){
            q.add(integer);
        }
        Double ans =0.0;
        while(m>0 && !q.isEmpty()){
            Integer top = q.poll();
            System.out.println(top);
            int eaten = Integer.valueOf((int) Math.floor(top / 2));
            System.out.println(eaten);
            q.add(top-eaten);
            m-=1;
        }
        while(!q.isEmpty()){
            ans+=q.poll();
        }
        System.out.println(ans);

        if(!apiLimiterService.isRequestAllowed(userId)){
            throw new ApiLimiterException("Too many requests");
        }
        List<CustomerOrder> orders = new ArrayList<>();
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        if(userId != null){
            orders = orderService.getAllOrdersForUser(PageRequest.of(page,size), userId);
        }
        else{
            orders = orderService.getAllOrders(PageRequest.of(page,size));
        }
        for(CustomerOrder customerOrder:orders){
            orderResponseDtoList.add(OrderResponseDto.createOrderResponseDto(customerOrder));
        }
        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);

    }


    // Update Order Status
    @PutMapping("/{id}/update")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id, @RequestParam String status) throws OrderNotFoundException {
        CustomerOrder customerOrder = orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>(OrderResponseDto.createOrderResponseDto(customerOrder),HttpStatus.ACCEPTED);
    }

    // Delete Order
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) throws OrderNotFoundException {
        orderService.deleteOrder(id);
        return new ResponseEntity<>("Order deleted successfully!", HttpStatus.ACCEPTED);
    }
    @GetMapping("/{id}/createPaymentLink")
    public ResponseEntity<String> createPaymentLinkForOrder(@PathVariable Long id) throws OrderNotFoundException, UserNotFoundException, PaymentLinkAlreadyCreatedException {
         PaymentLink paymentLink = paymentService.makePaymentForOrder(id);
        return new ResponseEntity<>(paymentLink.getShortUrl(), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<Refund> initiateRefundForOrder(@PathVariable Long id) throws RefundAlreadyCreatedException, OrderNotFoundException, PaymentNotCompletedException, PaymentLinkNotFoundException {
        return new ResponseEntity<>(paymentService.createRefundForOrder(id), HttpStatus.CREATED);
    }



        public static int tokenConsume(Integer price, Integer availableTokens, Integer nextTop){
            Integer tokens = 0;
            while (tokens<=availableTokens){
                Integer discountPrice = Integer.valueOf((int)Math.floor(price/Math.pow(2,tokens)));
                if(discountPrice>=nextTop){
                    tokens+=1;
                }
                else{
                    break;
                }
            }
            return tokens;

        }
        public void sample() {
            int[] prices = {2,4};
            int m = 2;
            int n = prices.length;
            Queue<Integer> q= new PriorityQueue<>((a,b)->{
                return b-a;
            });
            for(Integer integer:prices){
                q.add(Integer.valueOf(integer));
            }
            Double ans =0.0;
            while(m>0 && !q.isEmpty()){
                Integer top = q.poll();
                System.out.println(top);
                Integer nextTop = 0;
                if(q.isEmpty()){
                    Integer consumed = tokenConsume(top,m, Integer.MIN_VALUE);
                    Integer discountPrice = Integer.valueOf((int)Math.floor(top/Math.pow(2,consumed)));
                    System.out.println(discountPrice);
                    m-=consumed;
                    q.add(discountPrice);
                }
                else{
                    Integer consumed = tokenConsume(top,m,q.peek());
                    m-=consumed;
                    Integer discountPrice = Integer.valueOf((int)Math.floor(top/Math.pow(2,consumed)));
                    System.out.println(discountPrice);
                    q.add(discountPrice);
                }

            }
            while(!q.isEmpty()){
                ans+=q.poll();
            }
            System.out.println(ans);
        }


}
