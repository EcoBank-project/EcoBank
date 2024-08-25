package com.ecobank.app.chat.service;

import lombok.Data;

@Data
public class ChatCheckDTO {
	private boolean isParticipating;
    private boolean requiresPassword;
}
