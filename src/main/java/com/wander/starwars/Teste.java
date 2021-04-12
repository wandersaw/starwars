package com.wander.starwars;

import org.springframework.web.client.RestTemplate;

import com.wander.starwars.domain.SwapiPage;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Teste {
	
	public static void main(String[] args) {
		SwapiPage planet = new RestTemplate().getForObject("https://swapi.dev/api/planets/?search=tatoo", SwapiPage.class);
		log.info(planet);
	}

}
