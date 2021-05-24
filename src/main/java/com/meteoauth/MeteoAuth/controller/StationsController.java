package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.assembler.StationsAssembler;
import com.meteoauth.MeteoAuth.repository.StationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
//
//    @PostMapping("/stations")
//    public Stations addStations(@RequestBody Stations stations) {
//        stationsRepository.save(stations);
//        return stations;
//    }
//
//    @GetMapping("/stations/{id}")
//    public ResponseEntity<Stations> findById(@PathVariable("id") Integer measuredValuesId) {
//        Stations stations = stationsRepository.findById(measuredValuesId).orElseThrow(
//                () -> new ResouceNotFoundException("Station not found" + measuredValuesId));
//        return ResponseEntity.ok().body(stations);
//    }
//
//    @GetMapping("/stations")
//    public List<Stations> getStations() {
//        return stationsRepository.findAll();
//    }
//
//    @PutMapping("stations/{id}")
//    public ResponseEntity<Stations> updateStation(@PathVariable(value = "id") Integer stationId,
//                                                  @RequestBody Stations stationDetails) {
//        Stations station = stationsRepository.findById(stationId)
//                .orElseThrow(() -> new ResouceNotFoundException("Station not found for this id :: " + stationId));
//        station.setOwner_id(stationDetails.getOwner_id());
//        final Stations stations = stationsRepository.save(station);
//        return ResponseEntity.ok(stations);
//    }
//
//    @DeleteMapping("stations/{id}")
//    public ResponseEntity<Void> deleteStation(@PathVariable(value = "id") Integer stationId) {
//        Stations stationValues = stationsRepository.findById(stationId).orElseThrow(
//                () -> new ResouceNotFoundException("Station not found::: " + stationId));
//        stationsRepository.delete(stationValues);
//        return ResponseEntity.ok().build();
//    }
}
