package com.order_management.order_management.controller;

import com.order_management.order_management.entity.Order;
import com.order_management.order_management.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    Order order = orderService.getOrderById(id);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    Order createdOrder = orderService.createOrder(order);
    return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Order> updateOrder(
    @PathVariable Long id,
    @RequestBody Order updatedOrder
  ) {
    Order order = orderService.updateOrder(id, updatedOrder);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }
}
