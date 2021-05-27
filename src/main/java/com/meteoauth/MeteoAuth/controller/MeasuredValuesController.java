package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.assembler.MeasuredValuesAssembler;
import com.meteoauth.MeteoAuth.dto.MeasuredValuesDtoRequest;
import com.meteoauth.MeteoAuth.dto.MeasuredValuesDtoResponse;
import com.meteoauth.MeteoAuth.entities.MeasuredValue;
import com.meteoauth.MeteoAuth.repository.MeasuredValuesRepository;
import com.meteoauth.MeteoAuth.services.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/measured_values")
public class MeasuredValuesController {
    private final MeasuredValuesRepository measuredValuesRepository;
    private final MeasuredValuesAssembler measuredValuesAssembler;
    private final JwtUtil jwtUtil;

    public MeasuredValuesController(MeasuredValuesRepository measuredValuesRepository, MeasuredValuesAssembler measuredValuesAssembler, JwtUtil jwtUtil) {
        this.measuredValuesRepository = measuredValuesRepository;
        this.measuredValuesAssembler = measuredValuesAssembler;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/create/{stationTitle}")
    public MeasuredValuesDtoResponse createMeasuredValues(@RequestBody @Valid MeasuredValuesDtoRequest measuredValuesDtoRequest, @PathVariable("stationTitle") String stationTitle) {
        MeasuredValue measuredValue = measuredValuesAssembler.createMeasuredValues(measuredValuesDtoRequest, stationTitle);
        measuredValue = measuredValuesRepository.save(measuredValue);
        return measuredValuesAssembler.getMeasuredValuesDtoResponse(measuredValue);
    }

    @GetMapping("")
    public ResponseEntity<List<MeasuredValuesDtoResponse>> getMeasuredValues() {
        Iterable<MeasuredValue> measuredValuesList = measuredValuesRepository.findAll();
        return ResponseEntity.ok().body(measuredValuesAssembler.getMeasuredValuesDtoRequestList(measuredValuesList));
    }

//    @DeleteMapping("{time}")
//    public ResponseEntity<Void> deleteMeasuredValues(@PathVariable("measuredValueID") Long measuredValueID) {
//        MeasuredValue measuredValue = measuredValuesRepository.findById(measuredValueID);
//        if (measuredValue == null) {
//            throw new ResouceNotFoundException("MeasuredValue not found for this station :: " + measuredValueID);
//        }
//        measuredValuesRepository.delete(measuredValue);
//        return ResponseEntity.ok().build();
//    }
}
