package ca.sheridancollege.ohearonj.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User
{
	//All User elements
	private long	userId;
	
	@NonNull
	private String	username;
	
	@NonNull
	private String	email;
	
	@NonNull
	private String 	password;
}
