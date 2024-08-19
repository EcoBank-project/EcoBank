package com.ecobank.app.users.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    
	// 아이디 찾기(문자 본인인증)
	public String findUserIdByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    
	// 비밀번호 재설정(이메일 인증)
    public int updatePassword(@Param("useId") String useId, @Param("encodedPassword") String encodedPassword);
    
    // 구글 회원가입 여부
    public String findUserInfoByUseId(@Param("useId") String useId)
    ;
	// 유저 스코어
	@Select("SELECT NVL(SUM(score), 0) FROM score WHERE user_no = #{userNo} GROUP BY user_no")
	Integer findTotalScoreByUserNo(int userNo);

	// 유저 팔로워 수
	@Select("SELECT NVL(COUNT(f.follower_id), 0) FROM users u LEFT JOIN follow f ON u.user_no = f.following_id WHERE u.user_no = #{userNo} GROUP BY u.user_no")
	Integer findFollowerCountByUserNo(int userNo);

	// 유저 팔로잉 수
	@Select("SELECT NVL(COUNT(f.following_id), 0) FROM users u LEFT JOIN follow f ON f.follower_id = u.user_no WHERE u.user_no = #{userNo} GROUP BY u.user_no")
	Integer findFollowingCountByUserNo(int userNo);
	

}