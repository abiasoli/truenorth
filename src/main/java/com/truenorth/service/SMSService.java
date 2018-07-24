package com.truenorth.service;

import static com.truenorth.utils.MessageUtils.SYSTEM_INFO_SENDING_SMS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.truenorth.dto.DeliveryNotificationDto;

@Service
public class SMSService implements Sendable{

	private static final Logger LOGGER = LoggerFactory.getLogger(SMSService.class);
	
	@Override
	public void notifyDelivery(DeliveryNotificationDto notificationDto) {
		LOGGER.info(SYSTEM_INFO_SENDING_SMS, notificationDto);
	}

}
