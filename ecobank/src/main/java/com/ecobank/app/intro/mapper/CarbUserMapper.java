package com.ecobank.app.intro.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CarbUserMapper {
    @Select("{ CALL updateCountryInfo(#{userId, mode=IN, jdbcType=VARCHAR}, #{countryCode, mode=IN, jdbcType=VARCHAR}) }")
    void updateCountryInfo(@Param("userId") String userId, @Param("countryCode") String countryCode);
}