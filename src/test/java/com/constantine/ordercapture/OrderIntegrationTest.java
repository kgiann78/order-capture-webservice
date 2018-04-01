package com.constantine.ordercapture;

import com.constantine.ordercapture.model.Customer;
import com.constantine.ordercapture.model.Order;
import com.constantine.ordercapture.model.OrderStatus;
import com.constantine.ordercapture.repositories.CustomerRepository;
import com.constantine.ordercapture.repositories.OrderRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class OrderIntegrationTest extends BaseDatabaseTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void createOrder() {
        List<Order> orders = (List<Order>) orderRepository.findAll();

        assertThat(orders).isNotNull();
        assertThat(orders).isNotEmpty();

        Optional<Customer> customer = customerRepository.findById("alekos-1");

        logger.info("Found customer " + customer);

        int initialSize = orders.size();
        Order.Builder builder = new Order.Builder();
        Order order = orderRepository.save(builder
                .withCustomer(customer.orElse(new Customer()))
                .withOrderDate(new Date())
                .withLastUpdateDate(new Date())
                .withOrderStatus(OrderStatus.COMPLETED)
                .build());

        assertThat(order).isNotNull();

        orders = (List<Order>) orderRepository.findAll();

        assertThat(orders.size()).isGreaterThan(initialSize);
    }

    @Test
    public void findOrderById() {

        Optional<Order> order = orderRepository.findById("alekos-order-1");

        assertThat(order).isNotNull();
    }

    @Test
    public void findOrdersByCustomerId() {
        Optional<Customer> customer = customerRepository.findById("alekos-1");

        List<Order> orders = customer.map(orderRepository::findByCustomer).orElse(Collections.emptyList());

        assertThat(orders).isNotNull();
    }

    @Test
    public void deleteOrder() {
        Order.Builder builder = new Order.Builder();
        Optional<Customer> customer = customerRepository.findById("alekos-1");

        Order order = builder
                .withCustomer(customer.orElse(new Customer()))
                .withOrderDate(new Date())
                .withLastUpdateDate(new Date())
                .withOrderStatus(OrderStatus.COMPLETED)
                .build();

        order = orderRepository.save(order);

        orderRepository.deleteById(order.getId());
    }


}
