package com.truenorth.service;

import static com.truenorth.utils.MessageUtils.SYSTEM_INFO_SENDING_EMAIL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.truenorth.dto.DeliveryNotificationDto;

@Service
public class EmailService implements Sendable{

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	private JavaMailSender emailSender;
	
	@Autowired
	public EmailService(JavaMailSender emailSender){
		this.emailSender = emailSender;
	}

	@Override
	public void notifyDelivery(DeliveryNotificationDto notification) {
		StringBuilder sb = new StringBuilder();
	    sb.append("Hi ").append(notification.getRestaurantName()).append("! These are the details of your order:\n\n");
	    notification.getOrder().getMeals().forEach(meal -> {
	       sb.append("-. ").append(meal.getName()).append("\n");
	       });
	    sb.append("\nDelivery address: ").append(notification.getOrder().getAddress());
	    sb.append("\nDelivery time: ").append(notification.getOrder().getEta()).append(" hs");
	    sb.append("\nTotal to charge: $").append(notification.getOrder().getTotalCost());
	    sb.append("\n\nHave a nice day!\n\nRegards");
	    SimpleMailMessage email = new SimpleMailMessage();
	    email.setFrom("TrueNorth");
	    email.setTo(notification.getRestaurantEmail());
	    email.setSubject("A new order has been placed!");
	    email.setText(sb.toString());
	    emailSender.send(email);
	    LOGGER.info(SYSTEM_INFO_SENDING_EMAIL, notification);
	}
	
}
