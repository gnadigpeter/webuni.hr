package hu.webuni.airport.service;

import org.springframework.stereotype.Service;

@Service
public interface DiscountSevice {
	public int getDiscountPercent(int totalPrice);
}
