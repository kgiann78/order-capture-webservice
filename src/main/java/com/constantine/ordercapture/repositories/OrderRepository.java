package com.constantine.ordercapture.repositories;

import com.constantine.ordercapture.model.Customer;
import com.constantine.ordercapture.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface OrderRepository extends CrudRepository<Order, String> {

    List<Order> findByCustomer(Customer customer);
}
