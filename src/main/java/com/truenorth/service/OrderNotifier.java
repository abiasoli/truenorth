/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.truenorth.service;

import static com.truenorth.utils.MessageUtils.SYSTEM_INFO_ORDER_NOTIFIED;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.truenorth.dto.NotificationDto;

@Component
public class OrderNotifier {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderNotifier.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${cloudkarafka.topic}")
    private String topic;

    @Autowired
    public OrderNotifier(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(NotificationDto notification) {
    	
    	Message<NotificationDto> message = MessageBuilder
                .withPayload(notification)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
    	
        kafkaTemplate.send(message);
        LOGGER.info(SYSTEM_INFO_ORDER_NOTIFIED, message, topic);
    }

}
