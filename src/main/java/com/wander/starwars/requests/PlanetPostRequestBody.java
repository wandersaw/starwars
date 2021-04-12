package com.wander.starwars.requests;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class PlanetPostRequestBody {
	
	@Length(min = 3, message = "At least 3 characters are required for the planet name")
	private String name;
	private String climate;
	private String terrain;

}
