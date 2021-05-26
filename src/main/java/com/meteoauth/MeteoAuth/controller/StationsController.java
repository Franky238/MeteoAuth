package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.ResouceNotFoundException;
import com.meteoauth.MeteoAuth.assembler.StationsAssembler;
import com.meteoauth.MeteoAuth.dto.StationDtoRequest;
import com.meteoauth.MeteoAuth.dto.StationsDtoResponse;
import com.meteoauth.MeteoAuth.entities.Station;
import com.meteoauth.MeteoAuth.repository.StationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationsController {

    private final StationsRepository stationsRepository;
    private final StationsAssembler stationsAssembler;

    @Autowired
    public StationsController(StationsRepository stationsRepository, StationsAssembler stationsAssembler) {
        this.stationsRepository = stationsRepository;
        this.stationsAssembler = stationsAssembler;
    }

    @PostMapping("/add")
    public StationsDtoResponse addStation(@RequestBody @Valid StationDtoRequest stationDtoRequest) {
        Station station = stationsAssembler.getStation(stationDtoRequest);
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
