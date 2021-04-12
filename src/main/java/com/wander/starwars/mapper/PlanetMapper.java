package com.wander.starwars.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.wander.starwars.domain.Planet;
import com.wander.starwars.domain.SwapiPlanet;
import com.wander.starwars.requests.PlanetPostRequestBody;

@Mapper(componentModel = "spring")
public abstract class PlanetMapper {
	public static final PlanetMapper INSTANCE = Mappers.getMapper(PlanetMapper.class);

	public abstract Planet toPlanet(SwapiPlanet swapiPlanet);
	public abstract List<Planet> toPlanets(List<SwapiPlanet> swapiPlanet);
	
	public abstract Planet toPlanet(PlanetPostRequestBody swapiPlanet);
	
}