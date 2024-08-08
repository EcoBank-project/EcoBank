package com.ecobank.app.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecobank.app.admin.service.UserVO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginUserVO implements UserDetails{
	private UserVO userVO;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<>();
		auths.add(new SimpleGrantedAuthority(userVO.getRoleName()));
		
		return auths;
	}

	@Override
	public String getPassword() {
		return userVO.getPassword();
	}
	
	@Override
    public String getUsername() {
        return userVO.getUseId();
    }
	
	
	public String getNickname() {
		return userVO.getNickName();
	}
	
	public String getUserState() {
		return userVO.getUserState();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
