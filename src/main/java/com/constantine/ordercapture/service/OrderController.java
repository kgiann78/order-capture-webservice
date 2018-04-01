package com.constantine.ordercapture.service;

import com.constantine.ordercapture.exception.ResourceNotFoundException;
import com.constantine.ordercapture.model.*;
import com.constantine.ordercapture.repositories.CustomerRepository;
import com.constantine.ordercapture.repositories.OrderRepository;
import com.constantine.ordercapture.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/orders")
public class OrderController {

    private final String NO_ORDER =  "No order to add. Please check and try again!";
    private final String NO_CUSTOMER_ID =  "Customer ID is empty. Please check and try again!";
    private final String NO_PRODUCTIDS =  "There are no Product ID(s). Please check and try again!";
    private final String WRONG_CUSTOMER_ID = "Customer with id %s not found. Please check and try again!";
    private final String WRONG_PRODUCT_ID = "One or more Product IDs are not valid. Please check and try again!";
    private final String WRONG_ORDER = "Wrong Order ID. Please check and try again!";
    private final String MISSING_ORDER_ID = "Order Id is missing. Please check and try again!";
    private final String MISSING_ORDER_STATUS = "Order status is missing. Please check and try again!";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Iterable<Order> findAll() {

        return orderRepository.findAll();
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<Order> find(@RequestParam("id") String id) {

        if (id == null || id.isEmpty()) throw new ResourceNotFoundException(MISSING_ORDER_ID);

        return orderRepository.findById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    Order create(@RequestBody PlaceOrder placeOrder) {

        if (placeOrder == null) throw new ResourceNotFoundException(NO_ORDER);
        if (placeOrder.getCustomerID() == null || placeOrder.getCustomerID().isEmpty())
            throw new ResourceNotFoundException(NO_CUSTOMER_ID);
        if (placeOrder.getProductIDs() == null || placeOrder.getProductIDs().isEmpty())
            throw new ResourceNotFoundException(NO_PRODUCTIDS);


        // Searching for customer with customerID
        Optional<Customer> customer = customerRepository.findById(placeOrder.getCustomerID());
        if (!customer.isPresent()) throw new ResourceNotFoundException(String.format(WRONG_CUSTOMER_ID, placeOrder.getCustomerID()));

        // Searching for products with given productIDs
        List<Product> products = (List<Product>) productRepository.findAllById(placeOrder.getProductIDs());
        if (products.isEmpty()) throw new ResourceNotFoundException(WRONG_PRODUCT_ID);

        // Creating order
        Order.Builder builder = new Order.Builder();
        Order order = builder.withCustomer(customer.get())
                .withProducts(products)
                .withOrderDate(new Date())
                .withLastUpdateDate(new Date())
                .withOrderStatus(OrderStatus.NEW)
                .build();

        return orderRepository.save(order);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    void delete(@RequestParam("id") String id) {

        if (id == null || id.isEmpty()) throw new ResourceNotFoundException(MISSING_ORDER_ID);

        orderRepository.deleteById(id);
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    Order updateStatus(@RequestParam("id") String id, @RequestParam("status") OrderStatus orderStatus) {

        if (id == null || id.isEmpty()) throw new ResourceNotFoundException(MISSING_ORDER_ID);
        if (orderStatus == null) throw new ResourceNotFoundException(MISSING_ORDER_STATUS);

        Optional<Order> order = orderRepository.findById(id);

        if (!order.isPresent()) throw new ResourceNotFoundException(WRONG_ORDER);

        order.get().setOrderStatus(orderStatus);
        return orderRepository.save(order.get());
    }

}
