package com.spbstu.SneakerHunter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

@SpringBootApplication
public class SneakerHunterApplication {
	public static CurrencyUnit RUB = Monetary.getCurrency("RUB");;

	public static void main(String[] args) {
		SpringApplication.run(SneakerHunterApplication.class, args);
	}

}
