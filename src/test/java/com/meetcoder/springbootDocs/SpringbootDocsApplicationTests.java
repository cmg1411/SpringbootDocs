package com.meetcoder.springbootDocs;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest
class SpringbootDocsApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	void Configuration_에너테이션은_빈으로_등록되는가() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(SpringbootDocsApplication.class);

		Object bean = ac.getBean(SpringbootDocsApplication.class);

		Assertions.assertThat(bean).isInstanceOf(SpringbootDocsApplication.class);
	}
}
