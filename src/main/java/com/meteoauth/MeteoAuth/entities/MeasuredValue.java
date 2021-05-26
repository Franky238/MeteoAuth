package com.meteoauth.MeteoAuth.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "tb_measured_value")
public class MeasuredValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column
    private Long id;

    @Basic
    @Column
    private Timestamp measurementTime;

    @Basic
    @Column
    private Integer humidity;

    @Basic
    @Column
    private Integer temperature;

    @Basic
    @Column
    private Integer air_quality;

    @Basic
    @Column
    private Integer wind_speed;

    @Basic
    @Column
    private Integer wind_gusts;

    @Basic
    @Column
    private Integer wind_direction;

    @Basic
    @Column
    private Integer rainfall;

}
