package com.meteoauth.MeteoAuth.controller;//package com.example.cassandra.springbootclass.controller;
//
//import com.example.cassandra.springbootclass.ResouceNotFoundException;
//import com.example.cassandra.springbootclass.repository.MeasuredValuesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/measured_values")
//public class MeasuredValuesController {
//
//    @Autowired
//    MeasuredValuesRepository measuredValuesRepository;
//
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
//        measuredValues.setStation_id(measuredValuesDetails.getStation_id());//todo id => uuid
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
//}
