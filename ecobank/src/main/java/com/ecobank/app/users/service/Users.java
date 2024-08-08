package com.ecobank.app.users.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	private Integer userNo;
	
	@Column(name = "use_id")
	private String useId;

	@Column(name = "password")
	private String password;

	@Column(name = "nickname")
	private String nickName;

	@Column(name = "tell")
	private String tell;
	
	@Column(name = "createat")
	@CreationTimestamp // sysdate
	private Date createAt;
	
	@Column(name = "resp")
	private String resp = "A1"; // 일반회원
}
