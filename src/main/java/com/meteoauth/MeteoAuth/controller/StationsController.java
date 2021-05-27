package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.ResouceNotFoundException;
import com.meteoauth.MeteoAuth.assembler.StationsAssembler;
import com.meteoauth.MeteoAuth.dto.StationDtoRequest;
import com.meteoauth.MeteoAuth.dto.StationsDtoResponse;
import com.meteoauth.MeteoAuth.entities.Station;
import com.meteoauth.MeteoAuth.repository.StationsRepository;
import com.meteoauth.MeteoAuth.services.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationsController {
    private final StationsRepository stationsRepository;
    private final StationsAssembler stationsAssembler;
    private final JwtUtil jwtUtil;

    public StationsController(StationsRepository stationsRepository, StationsAssembler stationsAssembler, JwtUtil jwtUtil) {
        this.stationsRepository = stationsRepository;
        this.stationsAssembler = stationsAssembler;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/add")//todo add->create
    public StationsDtoResponse createStation(@RequestBody @Valid StationDtoRequest stationDtoRequest, @RequestHeader(name = "Authorization") String token) {
        String email = jwtUtil.extractEmail(token);
        Station station = stationsAssembler.createStation(stationDtoRequest, email);
        station = stationsRepository.save(station);
        return stationsAssembler.getStationDtoResponse(station);
    }

    @GetMapping("")
    public ResponseEntity<List<StationsDtoResponse>> getStations() {
        Iterable<Station> stationsList = stationsRepository.findAll();
        return ResponseEntity.ok().body(stationsAssembler.getStationDtoRequestList(stationsList));
    }

    @DeleteMapping("{title}")
    public ResponseEntity<Void> deleteStation(@PathVariable("title") String title) {
        Station station = stationsRepository.findByTitle(title);
        if (station == null) {
            throw new ResouceNotFoundException("Station not found for this title :: " + title);
        }
        stationsRepository.delete(station);
        return ResponseEntity.ok().build();
    }
}
