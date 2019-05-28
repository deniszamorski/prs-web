package com.prs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.User;

@RunWith(SpringRunner.class)
@JsonTest
public class UserJsonTests {

	@Autowired
	private JacksonTester<User> json;
	
	@Test
	public void serializeUserJsonTest() {
		User user = new User("tuser","pwd","fname","lname","phone","email",true,true);
		try {
			assertThat(json.write(user))
			.extractingJsonPathStringValue("$.password")
			.isEqualTo("pwd");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
