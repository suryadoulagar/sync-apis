package com.sync.backend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author surya
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

	private String message;
	private boolean success;
}
