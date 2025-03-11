package com.guptem.UberBackend;

import com.guptem.UberBackend.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberBackendApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;

	@Test
	void contextLoads() {
		emailSenderService.sendEmail(
				"xaxoh56811@kaiav.com",
				"This is the email subject",
				"body of the email"
		);
	}

	@Test
	void sendEmailsMultiple() {
		String emails[] = {
				"xaxoh56811@kaiav.com",
				"2303031560051@paruluniversity.ac.in"
		};
		emailSenderService.sendEmail(
				emails,
				"this is an email subject to test the uber backend",
				"this is its body!"
		);
	}

}
