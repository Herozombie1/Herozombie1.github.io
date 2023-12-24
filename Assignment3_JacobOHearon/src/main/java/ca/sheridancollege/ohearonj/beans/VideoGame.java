package ca.sheridancollege.ohearonj.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class VideoGame
{
	private long	videoGameId;
	
	@NonNull
	private String	title;
	
	private String 	publisher;
	private Float 	price;
	private String	description;
	private String	size;
	private Boolean microtransactions;
}
