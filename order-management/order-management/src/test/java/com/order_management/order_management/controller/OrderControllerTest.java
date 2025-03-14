package com.order_management.order_management.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.order_management.order_management.entity.Order;
import com.order_management.order_management.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

  @Mock
  private OrderService orderService;

  @InjectMocks
  private OrderController orderController;

  @Test
  void testGetOrderById() {
    Order order = new Order();
    order.setId(1L);
    when(orderService.getOrderById(1L)).thenReturn(order);

    ResponseEntity<Order> response = orderController.getOrderById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(order, response.getBody());
    verify(orderService, times(1)).getOrderById(1L);
  }

  @Test
  void testCreateOrder() {
    Order order = new Order();
    when(orderService.createOrder(order)).thenReturn(order);

    ResponseEntity<Order> response = orderController.createOrder(order);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(order, response.getBody());
    verify(orderService, times(1)).createOrder(order);
  }

  @Test
  void testUpdateOrder() {
    Order order = new Order();
    order.setId(1L);
    order.setCustomerName("Updated Customer Name");
    order.setProduct("Updated Product");
    order.setQuantity(10);
    order.setStatus("Completed");

    when(orderService.updateOrder(1L, order)).thenReturn(order);

    ResponseEntity<Order> response = orderController.updateOrder(1L, order);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(order, response.getBody());
    verify(orderService, times(1)).updateOrder(1L, order);
  }
}
