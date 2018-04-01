package com.constantine.ordercapture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@EnableJpaRepositories(basePackages = {"com.constantine.ordercapture.repositories"})
public class BaseDatabaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	@BeforeTransaction
	public void setupData() {

	    // deleting first ORDER_PRODUCT and order_table tables because they contains references to other tables
		deleteFromTables("ORDER_PRODUCT", "ORDER_TABLE", "PRODUCT", "CUSTOMER");
		executeSqlScript("classpath:test_db.sql", false);
	}

	@Test
	public void init() {

	}
}
