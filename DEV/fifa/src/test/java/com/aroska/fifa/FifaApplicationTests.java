package com.aroska.fifa;

import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.boot.test.context.SpringBootTest;

import com.aroska.fifa.tests.TesttypingTest;

@SpringBootTest
class FifaApplicationTests {

	@Test
	void contextLoads() {
		TesttypingTest ttt = new TesttypingTest();
		
		ttt.login();
	}

}
