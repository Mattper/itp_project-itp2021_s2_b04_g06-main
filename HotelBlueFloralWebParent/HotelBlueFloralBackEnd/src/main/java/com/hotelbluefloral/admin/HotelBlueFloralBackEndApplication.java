package com.hotelbluefloral.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.hotelbluefloral.comman.entity", "com.hotelbluefloral.admin.user"})
public class HotelBlueFloralBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBlueFloralBackEndApplication.class, args);
	}

}
