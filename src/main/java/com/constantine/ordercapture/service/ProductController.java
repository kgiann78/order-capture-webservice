package com.constantine.ordercapture.service;

import com.constantine.ordercapture.exception.ResourceNotFoundException;
import com.constantine.ordercapture.model.Order;
import com.constantine.ordercapture.model.Product;
import com.constantine.ordercapture.repositories.OrderRepository;
import com.constantine.ordercapture.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("/products")
public class ProductController {

    private final String NO_PRODUCT =  "No product to add. Please check and try again!";
    private final String MISSING_PRODUCT_ID = "Product Id is missing. Please check and try again!";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Iterable<Product> findAll() {

        return productRepository.findAll();
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<Product> find(@RequestParam("id") String id) {

        if (id == null || id.isEmpty()) throw new ResourceNotFoundException(MISSING_PRODUCT_ID);

        return productRepository.findById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    Product create(@RequestBody Product product) {

        if (product == null) throw new ResourceNotFoundException(NO_PRODUCT);

        return productRepository.save(product);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    void delete(@RequestParam("id") String id) {

        if (id == null || id.isEmpty()) throw new ResourceNotFoundException(MISSING_PRODUCT_ID);

        // Remove product from each order (removes entries to the ORDER_PRODUCT table)
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            for (Order order : product.get().getOrders()) {
                order.getProducts().remove(product.get());

                if (order.getProducts().isEmpty()) {
                    orderRepository.delete(order);
                }
            }
        }

        productRepository.deleteById(id);
    }
}
