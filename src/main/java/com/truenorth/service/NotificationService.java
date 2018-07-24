
package com.truenorth.service;

import static com.truenorth.utils.MessageUtils.SYSTEM_INFO_ORDER_RECEIVED;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.truenorth.dto.DeliveryNotificationDto;

@Service
public class NotificationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);
	private EmailService emailService;
	private SMSService smsService;
	
	@Autowired
	public NotificationService(EmailService emailService, SMSService smsService){
		this.emailService = emailService;
		this.smsService = smsService;
	}
	
    @KafkaListener(topics = "${cloudkarafka.topic}")
    public void processMessage(Message<DeliveryNotificationDto> message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                               @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
       
       LOGGER.info(SYSTEM_INFO_ORDER_RECEIVED, topics.get(0));
       DeliveryNotificationDto notification = message.getPayload();
       emailService.notifyDelivery(notification);
       smsService.notifyDelivery(notification);
    }

}
