package com.meteoauth.MeteoAuth.controller;


import com.meteoauth.MeteoAuth.repository.MeasuredValuesRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/measured_values")
public class MeasuredValuesController {
    private final MeasuredValuesRepository measuredValuesRepository;

    public MeasuredValuesController(MeasuredValuesRepository measuredValuesRepository) {
        this.measuredValuesRepository = measuredValuesRepository;
    }

//    @PostMapping("/measured_values")
//    public MeasuredValues addMeasuredValues(@RequestBody MeasuredValues measuredValues) {
//        measuredValuesRepository.save(measuredValues);
//        return measuredValues;
//    }
//
//    @GetMapping("/measured_values/{id}")
//    public ResponseEntity<MeasuredValues> findById(@PathVariable("id") Integer measuredValuesId) {
//        MeasuredValues measuredValues = measuredValuesRepository.findById(measuredValuesId).orElseThrow(
//                () -> new ResouceNotFoundException("Measured values not found" + measuredValuesId));
//        return ResponseEntity.ok().body(measuredValues);
//    }
//
//    @GetMapping("/measured_values")
//    public List<MeasuredValues> getMeasuredValues() {
//        return measuredValuesRepository.findAll();
//    }
//
//    @PutMapping("measured_values/{id}")
//    public ResponseEntity<MeasuredValues> updateMeasuredValues(@PathVariable(value = "id") Integer measuredValuesId,
//                                                               @RequestBody MeasuredValues measuredValuesDetails) {
//        MeasuredValues measuredValues = measuredValuesRepository.findById(measuredValuesId)
//                .orElseThrow(() -> new ResouceNotFoundException("Measured values not found for this id :: " + measuredValuesId));
//        measuredValues.setStation_id(measuredValuesDetails.getStation_id());
//        final MeasuredValues updatedMeasuredValues = measuredValuesRepository.save(measuredValues);
//        return ResponseEntity.ok(updatedMeasuredValues);
//    }
//
//    @DeleteMapping("measured_values/{id}")
//    public ResponseEntity<Void> deleteMeasuredValues(@PathVariable(value = "id") Integer measuredValuesId) {
//        MeasuredValues measuredValues = measuredValuesRepository.findById(measuredValuesId).orElseThrow(
//                () -> new ResouceNotFoundException("Measured values not found::: " + measuredValuesId));
//        measuredValuesRepository.delete(measuredValues);
//        return ResponseEntity.ok().build();
//    }
}
