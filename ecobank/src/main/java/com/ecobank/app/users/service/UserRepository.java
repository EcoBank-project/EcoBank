package com.ecobank.app.users.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// JPArepository : CRUD 기능 제공하는 인터페이스
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> { 
	Users findByUseId(String useId);
}