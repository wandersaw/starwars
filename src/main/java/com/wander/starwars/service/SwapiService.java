package com.wander.starwars.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.wander.starwars.domain.SwapiPage;
import com.wander.starwars.domain.SwapiPlanet;
import com.wander.starwars.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SwapiService {
	
	@Value("${services.swapi.url}")
	private String swapiUrl;
	
	@Value("${services.swapi.planets}")
	private String swapiPlanets;
	
	@Value("${services.swapi.search}")
	private String swapiSearch;

	@Value("${services.swapi.page}")
	private String swapiPage;

	@Value("${services.swapi.maxPages}")
	private Long swapiMaxPages;
	
	public SwapiPlanet findByName(String name) {
		StringBuilder url = new StringBuilder()
				.append(swapiUrl)
				.append(swapiPlanets)
				.append(swapiSearch);
		SwapiPage swapiPage = new RestTemplate().getForObject(url.toString(), SwapiPage.class, name);
		List<SwapiPlanet> planets = swapiPage.getSwapiPlanets();
		if (CollectionUtils.isEmpty(planets)) {
			throw new BadRequestException("This planet name does not exists in Star Wars API");
		} else if (planets.size() > 1) {
			String names = planets.stream().map(SwapiPlanet::getName).collect(Collectors.joining(", "));
			throw new BadRequestException("There is more than one planet with that name in Star Wars API: " + names);
		}
		return planets.get(0);
	}

	public SwapiPage findByPage(Long page) {
		StringBuilder url = new StringBuilder()
				.append(swapiUrl)
				.append(swapiPlanets)
				.append(swapiPage)
				.append(page);
		SwapiPage swapiPlanet = new RestTemplate().getForObject(url.toString(), SwapiPage.class);
		return swapiPlanet;
	}
	
	public List<SwapiPlanet> listAll() {
		StringBuilder url = new StringBuilder()
				.append(swapiUrl)
				.append(swapiPlanets)
				.append(swapiPage);
		long page = 1l;
		SwapiPage swapiPage = new RestTemplate().getForObject(url.toString(), SwapiPage.class, page++);
		if (CollectionUtils.isEmpty(swapiPage.getSwapiPlanets())) {
			throw new BadRequestException("There is no planet in Star Wars API");
		}
		List<SwapiPlanet> swapiPlanets = new ArrayList<SwapiPlanet>();
		swapiPlanets.addAll(swapiPage.getSwapiPlanets());
		while (StringUtils.hasText(swapiPage.getNext()) && page < swapiMaxPages) {
			swapiPage = new RestTemplate().getForObject(url.toString(), SwapiPage.class, page++);
			swapiPlanets.addAll(swapiPage.getSwapiPlanets());
		}
		
		return swapiPlanets;
	}
	
}
