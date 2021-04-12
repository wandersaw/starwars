package com.wander.starwars.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wander.starwars.domain.Planet;
import com.wander.starwars.domain.SwapiPlanet;
import com.wander.starwars.requests.PlanetPostRequestBody;
import com.wander.starwars.requests.PlanetPutRequestBody;
import com.wander.starwars.service.PlanetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("planets")
@RequiredArgsConstructor
public class PlanetController {
	
	private final PlanetService planetService;
	
	@GetMapping
	public ResponseEntity<List<Planet>> list() {
		return ResponseEntity.ok(planetService.listAll());
	}
	
	@GetMapping("/api")
	public ResponseEntity<List<SwapiPlanet>> listApi() {
		return ResponseEntity.ok(planetService.listAllApi());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Planet> findById(@PathVariable Long id) {
		return ResponseEntity.ok(planetService.findById(id));
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Planet>> findByName(@RequestParam String name) {
		return ResponseEntity.ok(planetService.findByName(name));
	}
	
	@PostMapping
	public ResponseEntity<Planet> save(@RequestBody @Valid PlanetPostRequestBody planetPostRequestBody) {
		return new ResponseEntity<Planet>(planetService.save(planetPostRequestBody), HttpStatus.CREATED);
	}
	
	@PostMapping("/saveFromApi")
	public ResponseEntity<Planet> saveFomApi(@RequestBody @Valid PlanetPostRequestBody planetPostRequestBody) {
		return new ResponseEntity<Planet>(planetService.saveFromApi(planetPostRequestBody), HttpStatus.CREATED);
	}
	
	@PostMapping("/saveAllFromApi")
	public ResponseEntity<Planet> saveAllFomApi() {
		return new ResponseEntity<Planet>(planetService.saveAllFomApi(), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		planetService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody PlanetPutRequestBody planetPutRequestBody) {
		planetService.replace(planetPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


}
