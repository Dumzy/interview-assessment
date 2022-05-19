package com.zinkwork.Atm.model.repository;

import com.zinkwork.Atm.model.AtmAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtmAdminRepository extends JpaRepository<AtmAdmin, Integer> {

    Optional<AtmAdmin> findById(int id);

}
