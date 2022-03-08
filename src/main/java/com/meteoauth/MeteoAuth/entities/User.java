package com.meteoauth.MeteoAuth.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column
    private Long id;

    @Basic
    @Column
    private String username;

    @Basic
    @Column(unique = true)
    private String email;

    @Basic
    @Column
    @JsonIgnore
    private String password;

    @Basic
    @Column
    private Timestamp registration_time = new Timestamp(System.currentTimeMillis());

    @Basic
    @Column
    private String city;

    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private Set<Station> stations;



    @Column(name = "PROVIDER_USER_ID")
    private String providerUserId;

    private String provider;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
