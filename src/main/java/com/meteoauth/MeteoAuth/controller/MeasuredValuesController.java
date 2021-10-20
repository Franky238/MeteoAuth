package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.ResouceNotFoundException;
import com.meteoauth.MeteoAuth.assembler.MeasuredValuesAssembler;
import com.meteoauth.MeteoAuth.dto.MeasuredValuesDtoRequest;
import com.meteoauth.MeteoAuth.dto.MeasuredValuesDtoResponse;
import com.meteoauth.MeteoAuth.entities.MeasuredValue;
import com.meteoauth.MeteoAuth.entities.Station;
import com.meteoauth.MeteoAuth.repository.MeasuredValuesRepository;
import com.meteoauth.MeteoAuth.repository.StationsRepository;
import com.meteoauth.MeteoAuth.repository.UserRepository;
import com.meteoauth.MeteoAuth.services.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/measured_values")
public class MeasuredValuesController {
    private final MeasuredValuesRepository measuredValuesRepository;
    private final MeasuredValuesAssembler measuredValuesAssembler;
    private final JwtUtil jwtUtil;
    private final StationsRepository stationsRepository;
    private final UserRepository userRepository;

    public MeasuredValuesController(MeasuredValuesRepository measuredValuesRepository, MeasuredValuesAssembler measuredValuesAssembler, JwtUtil jwtUtil, StationsRepository stationsRepository, UserRepository userRepository) {
        this.measuredValuesRepository = measuredValuesRepository;
        this.measuredValuesAssembler = measuredValuesAssembler;
        this.jwtUtil = jwtUtil;
        this.stationsRepository = stationsRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<MeasuredValuesDtoResponse> createMeasuredValues(@RequestBody @Valid MeasuredValuesDtoRequest measuredValuesDtoRequest,
                                                                          @RequestHeader(name = "Authorization") String token) {
        Long id = Long.parseLong(jwtUtil.extractSubject(token));

        MeasuredValue measuredValue = measuredValuesAssembler.createMeasuredValues(measuredValuesDtoRequest, id);
        measuredValue = measuredValuesRepository.save(measuredValue);
        return ResponseEntity.ok().body(measuredValuesAssembler.getMeasuredValuesDtoResponse(measuredValue));

    }

    @GetMapping("/all")//permit all
    public ResponseEntity<List<MeasuredValuesDtoResponse>> getMeasuredValues() {
        Iterable<MeasuredValue> measuredValuesList = measuredValuesRepository.findAll();
        return ResponseEntity.ok().body(measuredValuesAssembler.getMeasuredValuesDtoRequestList(measuredValuesList));
    }

    @GetMapping("/by-station")
    public ResponseEntity<List<MeasuredValuesDtoResponse>> getMeasuredValuesByStation(@RequestHeader(name = "Authorization") String token) {
        Long id =Long.parseLong(jwtUtil.extractSubject(token));
        Iterable<MeasuredValue> measuredValuesList = measuredValuesRepository.findByStation(stationsRepository.findById(id).get());
        return ResponseEntity.ok().body(measuredValuesAssembler.getMeasuredValuesDtoRequestList(measuredValuesList));
    }

    @GetMapping("/by-station/{id}")
    public ResponseEntity<List<MeasuredValuesDtoResponse>> getMeasuredValuesByStation(@PathVariable("id") Long stationID) {

        Iterable<MeasuredValue> measuredValuesList = measuredValuesRepository.findByStation(stationsRepository.findById(stationID).get());
        return ResponseEntity.ok().body(measuredValuesAssembler.getMeasuredValuesDtoRequestList(measuredValuesList));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOwnerMeasuredValues(@PathVariable("id") Long measuredValueID, @RequestHeader(name = "Authorization") String token) {
        Optional<MeasuredValue> optionalMeasuredValue = measuredValuesRepository.findById(measuredValueID);
        Long id =Long.parseLong(jwtUtil.extractSubject(token));
        MeasuredValue measuredValue = optionalMeasuredValue.get();
        if (!measuredValue.getStation().getId().equals(stationsRepository.findById(id).get())){
            return  ResponseEntity.notFound().build();
        }
        optionalMeasuredValue.ifPresent(measuredValuesRepository::delete);
        return ResponseEntity.ok().build();
    }
}
