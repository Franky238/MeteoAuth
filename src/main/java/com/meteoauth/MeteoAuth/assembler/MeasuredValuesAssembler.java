package com.meteoauth.MeteoAuth.assembler;

import com.meteoauth.MeteoAuth.dto.MeasuredValuesDtoRequest;
import com.meteoauth.MeteoAuth.dto.MeasuredValuesDtoResponse;
import com.meteoauth.MeteoAuth.entities.MeasuredValue;
import com.meteoauth.MeteoAuth.entities.Station;
import com.meteoauth.MeteoAuth.repository.StationsRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MeasuredValuesAssembler {
    private final StationsAssembler stationsAssembler;
    private final StationsRepository stationsRepository;

    public MeasuredValuesAssembler(StationsAssembler stationsAssembler, StationsRepository stationsRepository) {
        this.stationsAssembler = stationsAssembler;
        this.stationsRepository = stationsRepository;
    }

    public MeasuredValuesDtoResponse getMeasuredValuesDtoResponse(MeasuredValue measuredValue) {
        MeasuredValuesDtoResponse measuredValuesDtoResponse = new MeasuredValuesDtoResponse();
        measuredValuesDtoResponse.setId(measuredValue.getId());
        measuredValuesDtoResponse.setMeasurement_time(measuredValue.getMeasurementTime());
        measuredValuesDtoResponse.setHumidity(measuredValue.getHumidity());
        measuredValuesDtoResponse.setTemperature(measuredValue.getTemperature());
        measuredValuesDtoResponse.setAir_quality(measuredValue.getAir_quality());
        measuredValuesDtoResponse.setWind_speed(measuredValue.getWind_speed());
        measuredValuesDtoResponse.setWind_gusts(measuredValue.getWind_gusts());
        measuredValuesDtoResponse.setWind_direction(measuredValue.getWind_direction());
        measuredValuesDtoResponse.setRainfall(measuredValue.getRainfall());
        measuredValuesDtoResponse.setStation(stationsAssembler.getStationDtoResponse(measuredValue.getStation()));
        return measuredValuesDtoResponse;
    }

    public MeasuredValue createMeasuredValues(MeasuredValuesDtoRequest measuredValuesDtoRequest, Long id) {
        MeasuredValue measuredValue = new MeasuredValue();
        measuredValue.setHumidity(measuredValuesDtoRequest.getHumidity());
        measuredValue.setTemperature(measuredValuesDtoRequest.getTemperature());
        measuredValue.setAir_quality(measuredValuesDtoRequest.getAir_quality());
        measuredValue.setWind_speed(measuredValuesDtoRequest.getWind_speed());
        measuredValue.setWind_gusts(measuredValuesDtoRequest.getWind_gusts());
        measuredValue.setWind_direction(measuredValuesDtoRequest.getWind_direction());
        measuredValue.setRainfall(measuredValuesDtoRequest.getRainfall());
        Optional<Station> station = stationsRepository.findById(id);
        station.ifPresent(measuredValue::setStation);
        return measuredValue;
    }

    public List<MeasuredValuesDtoResponse> getMeasuredValuesDtoRequestList(Iterable<MeasuredValue> measuredValuesList) {

        List<MeasuredValuesDtoResponse> measuredValuesDtoResponses = new ArrayList<>();
        for (MeasuredValue measuredValue : measuredValuesList) {
            MeasuredValuesDtoResponse temp = new MeasuredValuesDtoResponse();
            temp.setId(measuredValue.getId());
            temp.setMeasurement_time(measuredValue.getMeasurementTime());
            temp.setHumidity(measuredValue.getHumidity());
            temp.setTemperature(measuredValue.getTemperature());
            temp.setAir_quality(measuredValue.getAir_quality());
            temp.setWind_speed(measuredValue.getWind_speed());
            temp.setWind_gusts(measuredValue.getWind_gusts());
            temp.setWind_direction(measuredValue.getWind_direction());
            temp.setRainfall(measuredValue.getRainfall());
            temp.setStation(stationsAssembler.getStationDtoResponse(measuredValue.getStation()));
            measuredValuesDtoResponses.add(temp);
        }
        return measuredValuesDtoResponses;
    }
}