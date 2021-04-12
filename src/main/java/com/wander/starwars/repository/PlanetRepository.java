package com.wander.starwars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wander.starwars.domain.Planet;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
	
	List<Planet> findByName(String name);
	
}
