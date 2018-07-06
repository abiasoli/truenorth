package com.truenorth.service;

import com.truenorth.dto.NotificationDto;

public interface Sendable {

	void send(NotificationDto notificationDto);
}
