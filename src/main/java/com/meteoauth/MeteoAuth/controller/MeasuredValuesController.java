package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.ResouceNotFoundException;
import com.meteoauth.MeteoAuth.assembler.MeasuredValuesAssembler;
import com.meteoauth.MeteoAuth.dto.MeasuredValuesDtoRequest;
import com.meteoauth.MeteoAuth.dto.MeasuredValuesDtoResponse;
import com.meteoauth.MeteoAuth.entities.MeasuredValue;
import com.meteoauth.MeteoAuth.repository.MeasuredValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/measured_values")
public class MeasuredValuesController {
    private final MeasuredValuesRepository measuredValuesRepository;
    private final MeasuredValuesAssembler measuredValuesAssembler;

    @Autowired
    public MeasuredValuesController(MeasuredValuesRepository measuredValuesRepository, MeasuredValuesAssembler measuredValuesAssembler) {
        this.measuredValuesRepository = measuredValuesRepository;
        this.measuredValuesAssembler = measuredValuesAssembler;
    }

    @PostMapping("/add")
    public MeasuredValuesDtoResponse addMeasuredValues(@RequestBody @Valid MeasuredValuesDtoRequest measuredValuesDtoRequest) {
        MeasuredValue measuredValue = measuredValuesAssembler.getMeasuredValues(measuredValuesDtoRequest);
        measuredValue = measuredValuesRepository.save(measuredValue);
        return measuredValuesAssembler.getMeasuredValuesDtoResponse(measuredValue);
    }

    @GetMapping("")
    public ResponseEntity<List<MeasuredValuesDtoResponse>> getMeasuredValues() {
        Iterable<MeasuredValue> measuredValuesList = measuredValuesRepository.findAll();
        return ResponseEntity.ok().body(measuredValuesAssembler.getMeasuredValuesDtoRequestList(measuredValuesList));
    }

    @DeleteMapping("{time}")
    public ResponseEntity<Void> deleteMeasuredValues(@PathVariable("time") String time) {
        MeasuredValue measuredValue = measuredValuesRepository.findByMeasurementTime(time);
        if (measuredValue == null) {
            throw new ResouceNotFoundException("MeasuredValue not found for this time :: " + time);
        }
        measuredValuesRepository.delete(measuredValue);
        return ResponseEntity.ok().build();
    }
}
