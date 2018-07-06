package com.truenorth.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class DateUtils {
	
	DateTimeFormatter etaFormatter = DateTimeFormatter.ofPattern("H:m");
	
	public DateTime now(){
		return DateTime.now();
	}

	public LocalDateTime toLocalDateTime(DateTime dateTime){
		return LocalDateTime.of(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(),
				dateTime.getHourOfDay(), dateTime.getMinuteOfHour(), dateTime.getSecondOfMinute());
	}
	
	public String formatETA(LocalDateTime eta) {
		return etaFormatter.format(eta);
	} 
}
