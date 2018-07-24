package com.truenorth.service;

import com.truenorth.dto.DeliveryNotificationDto;

public interface Sendable {

	void notifyDelivery(DeliveryNotificationDto notificationDto);
}
