package com.order_management.order_management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.order_management.order_management.entity.Order;
import com.order_management.order_management.exception.OrderException;
import com.order_management.order_management.repository.OrderRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @InjectMocks
  private OrderService orderService;

  @Test
  void testGetOrderById() {
    Order order = new Order();
    order.setId(1L);
    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

    Order result = orderService.getOrderById(1L);

    assertEquals(1L, result.getId());
    verify(orderRepository, times(1)).findById(1L);
  }

  @Test
  void testGetOrderByIdNotFound() {
    when(orderRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(OrderException.class, () -> orderService.getOrderById(1L));
    verify(orderRepository, times(1)).findById(1L);
  }

  @Test
  void testCreateOrder() {
    Order order = new Order();
    when(orderRepository.save(order)).thenReturn(order);

    Order result = orderService.createOrder(order);

    assertEquals(order, result);
    verify(orderRepository, times(1)).save(order);
  }

  @Test
  void testUpdateOrderStatus() {
    Order existingOrder = new Order();
    existingOrder.setId(1L);
    existingOrder.setStatus("Pending");

    Order updatedOrder = new Order();
    updatedOrder.setStatus("Completed");

    when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
    when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

    Order result = orderService.updateOrderStatus(1L, updatedOrder);

    assertEquals("Completed", result.getStatus());
    verify(orderRepository, times(1)).findById(1L);
    verify(orderRepository, times(1)).save(existingOrder);
  }

  @Test
  void testUpdateOrderStatusNotFound() {
    Order updatedOrder = new Order();
    updatedOrder.setStatus("Completed");

    when(orderRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(OrderException.class, () ->
      orderService.updateOrderStatus(1L, updatedOrder)
    );
    verify(orderRepository, times(1)).findById(1L);
  }
}
