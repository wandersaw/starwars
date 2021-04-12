package com.wander.starwars.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwapiPlanet {
	
	private String name;
	private String climate;
	private String terrain;
	private List<String> films;

}
