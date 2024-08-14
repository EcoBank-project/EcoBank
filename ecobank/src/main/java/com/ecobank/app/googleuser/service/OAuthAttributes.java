package com.ecobank.app.googleuser.service;

import java.util.Map;

import com.ecobank.app.users.service.Users;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
    private String useId; // 필드 추가

	@Builder
	public OAuthAttributes(Map<String, Object> attributes
			, String nameAttributeKey
			, String useId) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.useId = useId;
		  
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
			Map<String, Object> attributes) {
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		System.out.println("attributes : " + attributes);
		return OAuthAttributes.builder()
				.useId((String) attributes.get("email"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	public Users toEntity() {
		return Users.builder()
				.useId(useId)
				.build();
	}
}
