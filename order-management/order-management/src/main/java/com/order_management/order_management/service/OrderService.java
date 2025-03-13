package com.order_management.order_management.service;

import com.order_management.order_management.entity.Order;
import com.order_management.order_management.exception.OrderException;
import com.order_management.order_management.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order getOrderById(Long id) {
    return orderRepository
      .findById(id)
      .orElseThrow(() -> new OrderException("Order not found with id: " + id));
  }

  public Order createOrder(Order order) {
    return orderRepository.save(order);
  }

  public Order updateOrderStatus(Long id, Order updatedOrder) {
    Order existingOrder = orderRepository
      .findById(id)
      .orElseThrow(() -> new OrderException("Order not found with id: " + id));
    existingOrder.setStatus(updatedOrder.getStatus());
    return orderRepository.save(existingOrder);
  }
}
