package com.meteoauth.MeteoAuth.assembler;

import com.meteoauth.MeteoAuth.dto.StationDtoRequest;
import com.meteoauth.MeteoAuth.dto.StationsDtoResponse;

import com.meteoauth.MeteoAuth.entities.Station;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StationsAssembler {
    public StationsDtoResponse getStationDtoResponse(Station station) {
        StationsDtoResponse stationsDtoResponse = new StationsDtoResponse();
        stationsDtoResponse.setDestination(station.getDestination());
        stationsDtoResponse.setModel_description(station.getModel_description());
        stationsDtoResponse.setPhone(station.getPhone());
        stationsDtoResponse.setRegistration_time(station.getRegistration_time());
        stationsDtoResponse.setTitle(station.getTitle());
        return stationsDtoResponse;
    }

    public Station getStation(StationDtoRequest stationDtoRequest) {
        Station station = new Station();
        station.setDestination(stationDtoRequest.getDestination());
        station.setModel_description(stationDtoRequest.getModel_description());
        station.setPhone(stationDtoRequest.getPhone());
        station.setRegistration_time(stationDtoRequest.getRegistration_time());
        station.setTitle(stationDtoRequest.getTitle());

        return station;
    }

    public List<StationsDtoResponse> getStationDtoRequestList(Iterable<Station> stationList) {
        List<StationsDtoResponse> stationsDtoResponses = new ArrayList<>();
        for (Station station : stationList) {
            StationsDtoResponse temp = new StationsDtoResponse();
            temp.setDestination(station.getDestination());
            temp.setModel_description(station.getModel_description());
            temp.setPhone(station.getPhone());
            temp.setRegistration_time(station.getRegistration_time());
            temp.setTitle(station.getTitle());
            stationsDtoResponses.add(temp);
        }
        return stationsDtoResponses;
    }

}
