package com.truenorth.connector;

import static com.truenorth.utils.MessageUtils.SYSTEM_ERROR;
import static com.truenorth.utils.MessageUtils.SYSTEM_ERROR_DURATION_NOT_FOUND;
import static com.truenorth.utils.MessageUtils.USER_ERROR_DURATION_NOT_FOUND;

import org.joda.time.ReadableInstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.truenorth.config.GoogleApiConfiguration;

import javassist.NotFoundException;

@Component
public class GeoConnector {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GeoConnector.class);
	private GoogleApiConfiguration config;

	@Autowired
	public GeoConnector(GoogleApiConfiguration config) {
		this.config = config;
	}

	public int getTransitTime(LatLng origin, LatLng destination, ReadableInstant departureTime){
		
		GeoApiContext context = new GeoApiContext().setApiKey(config.getApikey());
		    
	    try {
	        DistanceMatrix distanceMatrix = DistanceMatrixApi.newRequest(context)
	                .origins(origin)
	                .destinations(destination)
	                .mode(TravelMode.DRIVING)
	                .departureTime(departureTime)
	                .await();

	        if( distanceMatrix.rows.length == 0 || distanceMatrix.rows[0].elements.length == 0 ){
	        	LOGGER.error(SYSTEM_ERROR_DURATION_NOT_FOUND);
	        	throw new NotFoundException(USER_ERROR_DURATION_NOT_FOUND);
	        }

	        return (int) distanceMatrix.rows[0].elements[0].duration.inSeconds;
	        
	    } catch (Exception e) {
	    	LOGGER.error(SYSTEM_ERROR, e.getMessage());
	        throw new RuntimeException(e);
	    }
	}
}
