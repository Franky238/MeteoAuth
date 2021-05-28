package com.meteoauth.MeteoAuth.repository;

import com.meteoauth.MeteoAuth.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
