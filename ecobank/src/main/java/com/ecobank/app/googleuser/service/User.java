/*
 * package com.ecobank.app.googleuser.service;
 * 
 * import javax.persistence.Column; import javax.persistence.Entity; import
 * javax.persistence.GeneratedValue; import javax.persistence.GenerationType;
 * import javax.persistence.Id;
 * 
 * import lombok.Builder; import lombok.Getter; import lombok.NoArgsConstructor;
 * 
 * @Entity
 * 
 * @NoArgsConstructor
 * 
 * @Getter public class User {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
 * // 시퀀스 적용 private Integer userNo;
 * 
 * @Column(name = "use_id") private String useId;
 * 
 * @Column(name = "nickname") private String nickName;
 * 
 * @Column(name = "tell") private String tell;
 * 
 * @Column(name = "resp") private String resp = "A1"; // 일반회원
 * 
 * @Builder public User(String useId, String nickName, String tell, String resp)
 * { this.useId = useId; this.nickName = nickName; this.tell = tell; this.resp =
 * resp; }
 * 
 * }
 */