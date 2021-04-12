package com.wander.starwars.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwapiPage {
	
	private Long count;
	private String next;
	private String previous;
	@JsonProperty("results")
	private List<SwapiPlanet> swapiPlanets;
	
}