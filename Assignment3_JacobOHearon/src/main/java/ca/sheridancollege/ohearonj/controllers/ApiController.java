package ca.sheridancollege.ohearonj.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.ohearonj.beans.VideoGame;
import ca.sheridancollege.ohearonj.database.DatabaseAccess;

@RestController
@RequestMapping("/api/a3/videoGames")
public class ApiController
{
	@Autowired
	private DatabaseAccess da;
	
	@GetMapping
	public List<VideoGame> getAllVideoGames()
	{
		return da.getAllVideoGames();
	}
}
