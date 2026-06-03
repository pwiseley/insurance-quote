package dev.petiton.insurance_quote;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
		properties = {
				"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
		})
class InsuranceQuoteApplicationTests {

	@Test
	void contextLoads() {
	}

}
