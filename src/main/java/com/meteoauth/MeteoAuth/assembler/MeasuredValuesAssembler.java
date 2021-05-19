package com.meteoauth.MeteoAuth.assembler;//package com.example.cassandra.springbootclass.assembler;
//
//import com.example.cassandra.springbootclass.dto.MeasuredValuesDtoRequest;
//
//public class MeasuredValuesAssembler {
//    public static MeasuredValues toEntity(MeasuredValuesDtoRequest dto) {
//        MeasuredValues measuredValues = new MeasuredValues();
//        measuredValues.setHumidity(dto.getHumidity());
//        measuredValues.setMeasurement_time(dto.getMeasurement_time());
//        measuredValues.setRainfall(dto.getRainfall());
//        measuredValues.setAir_quality(dto.getAir_quality());
//        measuredValues.setTemperature(dto.getTemperature());
//        measuredValues.setWind_direction(dto.getWind_direction());
//        measuredValues.setWind_gusts(dto.getWind_gusts());
//        measuredValues.setWind_speed(dto.getWind_speed());
//        return measuredValues;
//    }
//
//    public static MeasuredValuesDtoRequest toDto(MeasuredValues measuredValues) {
//        MeasuredValuesDtoRequest dto = new MeasuredValuesDtoRequest();
//        dto.setHumidity(measuredValues.getHumidity());
//        dto.setMeasurement_time(measuredValues.getMeasurement_time());
//        dto.setRainfall(measuredValues.getRainfall());
//        dto.setTemperature(measuredValues.getTemperature());
//        dto.setAir_quality(measuredValues.getAir_quality());
//        dto.setWind_direction(measuredValues.getWind_direction());
//        dto.setWind_gusts(measuredValues.getWind_gusts());
//        dto.setWind_speed(measuredValues.getWind_speed());
//        return dto;
//    }
//}
