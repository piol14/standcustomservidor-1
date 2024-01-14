package com.ElenaOrtega.standcustom.repository;



import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ElenaOrtega.standcustom.entity.UserEntity;



public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "ALTER TABLE cliente AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}