package com.ecobank.app.users.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq") // 시퀀스 적용
	private Integer userNo;
	
	@Column(name = "use_id")
	private String useId;

	@Column(name = "password")
	private String password;

	@Column(name = "nickname")
	private String nickName;

	@Column(name = "user_state")
	private String userState;
	
	@Column(name = "tell")
	private String tell;
	
	@Column(name = "createat")
	@CreationTimestamp // sysdate
	private Date createAt;
	
	@Column(name = "resp")
	private String resp = "A1"; // 일반회원
 
    public void deactivate() {
        this.nickName = null;
        this.password = null;
        this.tell = null;
        this.resp = null;
        this.userState = null;
    }
}
