package com.meteoauth.MeteoAuth.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor//added
public class User {
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
    private String password;

    @Basic
    @Column
    private Timestamp registration_time = new Timestamp(System.currentTimeMillis());
    //  todo correct way?
    //    @Basic(optional = false)
    //    @Column(name = "LastTouched", insertable = false, updatable = false)
    //    @Temporal(TemporalType.TIMESTAMP)
    //    private Date lastTouched;

    @Basic
    @Column
    private String city;

    @Basic
    @Column
    private boolean enabled;

    @Basic
    @Column
    private boolean tokenExpired; //todo necessarily?

    @OneToMany(mappedBy = "user")
    private Set<Station> stations;


    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
}
