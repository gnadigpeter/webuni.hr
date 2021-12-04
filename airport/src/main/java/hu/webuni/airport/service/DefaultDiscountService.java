package hu.webuni.airport.service;

import java.io.ObjectInputFilter.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.airport.config.AirportConfigProperties;

@Service
public class DefaultDiscountService implements DiscountSevice {

	@Autowired
	AirportConfigProperties congfig;
	
	@Override
	public int getDiscountPercent(int totalPrice) {
		return congfig.getDiscount().getDef().getPercent();
	}

}
