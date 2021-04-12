package com.wander.starwars.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.wander.starwars.domain.Planet;
import com.wander.starwars.domain.SwapiPlanet;
import com.wander.starwars.exception.BadRequestException;
import com.wander.starwars.mapper.PlanetMapper;
import com.wander.starwars.repository.PlanetRepository;
import com.wander.starwars.requests.PlanetPostRequestBody;
import com.wander.starwars.requests.PlanetPutRequestBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class PlanetService {
	
	private final SwapiService swapiService;
	private final PlanetRepository planetRepository;
	
	public List<Planet> listAll() {
		return planetRepository.findAll();
	}
	
	public List<SwapiPlanet> listAllApi() {
		return swapiService.listAll();
	}
	
	public Planet findById(Long id) {
		return planetRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Planet not found"));
	}
	
	public List<Planet> findByName(String name) {
		return planetRepository.findByName(name);
	}

	@Transactional
	public Planet saveFromApi(PlanetPostRequestBody planetPostRequestBody) {
		SwapiPlanet swapiplanet = swapiService.findByName(planetPostRequestBody.getName());
		return saveSwapiPlanet(swapiplanet);
	}

	private Planet saveSwapiPlanet(SwapiPlanet swapiplanet) {
		List<Planet> savedPlanet = this.findByName(swapiplanet.getName());
		if (CollectionUtils.isEmpty(savedPlanet)) {
			Planet planet = PlanetMapper.INSTANCE.toPlanet(swapiplanet);
			List<String> films = swapiplanet.getFilms();
			Long filmAppearences = CollectionUtils.isEmpty(films) ? 0l : films.size();
			planet.setFilmAppearences(filmAppearences);
			return planetRepository.save(planet);
		} else {
			throw new BadRequestException("This planet is already registered in the base");
		}
	}
	
	@Transactional
	public Planet save(PlanetPostRequestBody planetPostRequestBody) {
		return planetRepository.save(PlanetMapper.INSTANCE.toPlanet(planetPostRequestBody));
	}
	
	public void delete(Long id) {
		planetRepository.delete(findById(id));
	}

	public void replace(PlanetPutRequestBody planetPutRequestBody) {
//		Planet savedPlanet = findById(planetPutRequestBody.getId());
//		Planet planet = PlanetMapper.INSTANCE.toPlanet(planetPutRequestBody);
//		planet.setId(savedPlanet.getId());
//		planetRepository.save(planet);
	}

	public Planet saveAllFomApi() {
		List<SwapiPlanet> swapiPlanets = swapiService.listAll();
		for (SwapiPlanet swapiPlanet : swapiPlanets) {
			try {
				saveSwapiPlanet(swapiPlanet);
			} catch (BadRequestException e) {
				log.warn("Error saving the planet {}", swapiPlanet.getName());
				log.warn("See the problem: {} ", e.getMessage());
			}
		};
		return null;
	}

}
