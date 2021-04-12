package com.wander.starwars.requests;

import lombok.Data;

@Data
public class PlanetPutRequestBody {
	
	private Long id;
	private String name;
	private String climate;
	private String terrain;

}
