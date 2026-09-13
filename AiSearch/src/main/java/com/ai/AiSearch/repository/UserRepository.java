package com.ai.AiSearch.repository;

import com.ai.AiSearch.entity.RealUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<RealUser,Long> {

    Optional<RealUser> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<RealUser> findById(Long id);

}
