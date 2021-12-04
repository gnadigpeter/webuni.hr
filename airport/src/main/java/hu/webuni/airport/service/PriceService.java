package hu.webuni.airport.service;

import org.springframework.stereotype.Service;

@Service
public class PriceService {
	private DiscountSevice discountSevice;
	
	
	public PriceService(DiscountSevice discountSevice) {
		this.discountSevice = discountSevice;
	}

	public int getFinalPrice(int price) {
		return (int) (price / 100.0 * (100 - discountSevice.getDiscountPercent(price)));
	}
}
