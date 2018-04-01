package com.constantine.ordercapture;

import com.constantine.ordercapture.model.Order;
import com.constantine.ordercapture.model.Product;
import com.constantine.ordercapture.repositories.OrderRepository;
import com.constantine.ordercapture.repositories.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class ProductIntegrationTest extends BaseDatabaseTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void createProduct() {
        List<Product> products = (List<Product>) productRepository.findAll();

        assertThat(products).isNotNull();
        assertThat(products).isNotEmpty();


        int initialSize = products.size();
        Product.Builder builder = new Product.Builder();
        Product order = productRepository.save(builder.withId("portokalia-2")
                .withName("portokalia merlin")
                .withDescription("1 kg portokalia syskeyasmena")
                .withSKU("1234")
                .build());

        assertThat(order).isNotNull();

        products = (List<Product>) productRepository.findAll();

        assertThat(products.size()).isGreaterThan(initialSize);
    }

    @Test
    public void findProductById() {

        Optional<Product> order = productRepository.findById("portokalia-1");

        assertThat(order).isNotNull();
    }

    @Test
    public void findProductsByOrder() {
        Optional<Order> order = orderRepository.findById("alekos-order-1");

        assertThat(order).isPresent();

        if(order.isPresent()) {

            List<Product> products = order.get().getProducts();

            assertThat(products).isNotNull().isNotEmpty();
        }
    }

    @Test
    public void deleteProduc() {
        Product.Builder builder = new Product.Builder();
        Product product = builder.withSKU("1234").withDescription("product to be deleted").withName("DELETE_ME").build();


        product = productRepository.save(product);

        logger.info("New product created: " + product);


        productRepository.deleteById(product.getId());

        Optional<Product> retrieveProduct = productRepository.findById(product.getId());

        assertThat(retrieveProduct).isEmpty();
    }

}
