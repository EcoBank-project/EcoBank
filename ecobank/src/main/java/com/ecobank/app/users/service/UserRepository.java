package com.ecobank.app.users.service;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// JPArepository : CRUD 기능 제공하는 인터페이스
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	Optional<Users> findByUseId(String useId);

	@Query("SELECT u.password FROM Users u WHERE u.userNo = :userNo")
	String findPasswordByUserNo(@Param("userNo") Integer userNo);
}