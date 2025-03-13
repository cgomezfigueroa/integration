package com.order_management.order_management;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class OrderManagementApplicationTests {

  @MockitoBean
  private ApplicationContext applicationContext;

  @Test
  void contextLoads() {
    assertNotNull(applicationContext, "Application context should not be null");
  }

  @Test
  void main() {
    OrderManagementApplication.main(new String[] {});
  }
}
