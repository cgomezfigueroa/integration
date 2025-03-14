package com.order_management.order_management.service;

import com.order_management.order_management.entity.Order;
import com.order_management.order_management.exception.OrderException;
import com.order_management.order_management.kafka.KafkaProducer;
import com.order_management.order_management.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final KafkaProducer kafkaProducer;

  @Autowired
  public OrderService(
    OrderRepository orderRepository,
    KafkaProducer kafkaProducer
  ) {
    this.orderRepository = orderRepository;
    this.kafkaProducer = kafkaProducer;
  }

  public Order getOrderById(Long id) {
    return orderRepository
      .findById(id)
      .orElseThrow(() -> new OrderException("Order not found with id: " + id));
  }

  public Order createOrder(Order order) {
    Order createdOrder = orderRepository.save(order);
    kafkaProducer.sendMessage("Order created: " + createdOrder.toString());
    return createdOrder;
  }

  public Order updateOrder(Long id, Order updatedOrder) {
    Order existingOrder = orderRepository
      .findById(id)
      .orElseThrow(() -> new OrderException("Order not found with id: " + id));

    existingOrder.setCustomerName(updatedOrder.getCustomerName());
    existingOrder.setProduct(updatedOrder.getProduct());
    existingOrder.setQuantity(updatedOrder.getQuantity());
    existingOrder.setStatus(updatedOrder.getStatus());

    Order updatedOrderEntity = orderRepository.save(existingOrder);
    kafkaProducer.sendMessage(
      "Order updated: " + updatedOrderEntity.toString()
    );
    return updatedOrderEntity;
  }
}
