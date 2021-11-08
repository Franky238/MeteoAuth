package com.meteoauth.MeteoAuth.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor//added
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
    //  todo correct way?
    //    @Basic(optional = false)
    //    @Column(name = "LastTouched", insertable = false, updatable = false)
    //    @Temporal(TemporalType.TIMESTAMP)
    //    private Date lastTouched;

    @Basic
    @Column
    private String city;

   // @Column(name = "enabled", columnDefinition = "BIT", length = 1)
    private boolean enabled;

    @Basic
    @Column
    private boolean tokenExpired;

    @OneToMany(mappedBy = "user")
    private Set<Station> stations;



    @Column(nullable = false)
    private String name;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;





   // private static final long serialVersionUID = 65981149772133526L;



    @Column(name = "PROVIDER_USER_ID")
    private String providerUserId;





    @Column(name = "DISPLAY_NAME")
    private String displayName;

//
// //   @Column(name = "created_date", nullable = false, updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    protected Date createdDate;
//
//   @Temporal(TemporalType.TIMESTAMP)
//    protected Date modifiedDate;

    private String provider;

//    // bi-directional many-to-many association to Role
//    @JsonIgnore
//    @ManyToMany
//    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
//    private Set<Role> roles;


    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;



    private static final long serialVersionUID = 65981149772133526L;


    @Column(name = "created_date")//
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;



}
