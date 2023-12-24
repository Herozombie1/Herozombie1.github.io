package ca.sheridancollege.ohearonj.controllers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

import ca.sheridancollege.ohearonj.database.DatabaseAccess;
import ca.sheridancollege.ohearonj.beans.VideoGame;

@Controller
public class VideoGameController
{	
	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	//The globals
	List<VideoGame> videoGameList = new CopyOnWriteArrayList<VideoGame>();
	
	List<VideoGame> ownedGamesList = new CopyOnWriteArrayList<VideoGame>();
	
	List<String> userRoles = new CopyOnWriteArrayList<>();
	
	//================================================================================================//
	//																								  //
	//										Browse Games Functions									  //
	//																								  //
	//================================================================================================//

	
	@GetMapping("/browseGames")
	public String index(Model model, @ModelAttribute VideoGame videoGame)
	{	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUsername = "";
		Boolean logStatus = null;
		Long userId = null;
		String userRole = "";
		
		try
		{
			currentUsername = da.getUserByName(authentication.getName()).getUsername();
			userId = da.getUserByName(currentUsername).getUserId();
			userRoles = da.getRolesById(userId);
			userRole = userRoles.isEmpty() ? "No Role" : userRoles.get(0);
			logStatus = true;
		}
		catch(Exception e)
		{
			currentUsername = "None";
			logStatus = false;
		}
		
		model.addAttribute("videoGame"		,new VideoGame());
		model.addAttribute("videoGameList"	,da.getAllVideoGames());
		model.addAttribute("username"    	,currentUsername);
		model.addAttribute("userRole"		,userRole);
		model.addAttribute("logStatus"		,logStatus);
		
		return "browseGames";
	}
	
	@PostMapping("/insertVideoGame")
	public String insertVideoGame(Model model, @ModelAttribute VideoGame videoGame)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUsername = "";
		Boolean logStatus = null;
		Long userId = null;
		String userRole = "";
		
		try
		{
			currentUsername = da.getUserByName(authentication.getName()).getUsername();
			userId = da.getUserByName(currentUsername).getUserId();
			userRoles = da.getRolesById(userId);
			userRole = userRoles.isEmpty() ? "No Role" : userRoles.get(0);
			logStatus = true;
		}
		catch(Exception e)
		{
			currentUsername = "None";
			logStatus = false;
		}
		
		da.insertVideoGame(videoGame);
		
		model.addAttribute("videoGame"		,new VideoGame());
		model.addAttribute("videoGameList"	,da.getAllVideoGames());
		model.addAttribute("username"    	,currentUsername);
		model.addAttribute("userRole"		,userRole);
		model.addAttribute("logStatus"		,logStatus);
		
		return "browseGames";
	}
	
	@PostMapping("/editVideoGame/{videoGameId}")
	public String editBook(Model model, @ModelAttribute VideoGame videoGame)
	{	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUsername = "";
		Boolean logStatus = null;
		Long userId = null;
		String userRole = "";
		
		try
		{
			currentUsername = da.getUserByName(authentication.getName()).getUsername();
			userId = da.getUserByName(currentUsername).getUserId();
			userRoles = da.getRolesById(userId);
			userRole = userRoles.isEmpty() ? "No Role" : userRoles.get(0);
			logStatus = true;
		}
		catch(Exception e)
		{
			currentUsername = "None";
			logStatus = false;
		}
		
		da.editVideoGame(videoGame);
		
		model.addAttribute("videoGame"		,videoGame);
		model.addAttribute("videoGameList"	,da.getAllVideoGames());
		model.addAttribute("username"    	,currentUsername);
		model.addAttribute("userRole"		,userRole);
		model.addAttribute("logStatus"		,logStatus);
		model.addAttribute("user"			,da.getUserByName(currentUsername));
		
		return "browseGames";
	}
	
	
	@GetMapping("/deleteVideoGame/{videoGameId}")
	public String deleteBook(Model model, @PathVariable Long videoGameId)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUsername = "";
		Boolean logStatus = null;
		Long userId = null;
		String userRole = "";
		
		try
		{
			currentUsername = da.getUserByName(authentication.getName()).getUsername();
			userId = da.getUserByName(currentUsername).getUserId();
			userRoles = da.getRolesById(userId);
			userRole = userRoles.isEmpty() ? "No Role" : userRoles.get(0);
			logStatus = true;
		}
		catch(Exception e)
		{
			currentUsername = "None";
			logStatus = false;
		}
		
		da.deleteVideoGame(videoGameId);
		
		model.addAttribute("videoGame"		,new VideoGame());
		model.addAttribute("videoGameList"	,da.getAllVideoGames());
		model.addAttribute("username"  		,currentUsername);
		model.addAttribute("userRole"		,userRole);
		model.addAttribute("logStatus"		,logStatus);
		
		return "browseGames";
	}
	
	@GetMapping("/viewDetails/{videoGameId}")
	public String viewDetails(Model model, HttpSession session, @PathVariable Long videoGameId)
	{	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUsername = "";
		Boolean logStatus = null;
		Long userId = null;
		String userRole = "";
		
		try
		{
			 currentUsername = da.getUserByName(authentication.getName()).getUsername();
			 userId = da.getUserByName(currentUsername).getUserId();
			 userRoles = da.getRolesById(userId);
			 userRole = userRoles.isEmpty() ? "No Role" : userRoles.get(0);
			 logStatus = true;
		}
		catch(Exception e)
		{
			currentUsername = "None";
			logStatus = false;
		}
		
		VideoGame videoGame = da.getVideoGameById(videoGameId);
		
		model.addAttribute("username" 	,currentUsername);
		model.addAttribute("userRole"   ,userRole);
		model.addAttribute("logStatus"	,logStatus);
		model.addAttribute("videoGame"	,videoGame);
		
		return "gameDetails";
	}
	
	@GetMapping("/viewOwnedGames")
	public String viewOwnedGames(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUsername = "";
		String userRole = "";
		
		ownedGamesList.clear();

		Boolean logStatus = true;
		Long userId = da.getUserByName(authentication.getName()).getUserId();
		currentUsername = da.getUserByName(authentication.getName()).getUsername();
		
		for(VideoGame ownedGameId : da.getOwnedGamesByUserId(userId))
		{
			Long videoGameId = ownedGameId.getVideoGameId();
			
			VideoGame videoGame = da.getVideoGameById(videoGameId);
			
			ownedGamesList.add(videoGame);
		}
		
		model.addAttribute("username"    	,currentUsername);
		model.addAttribute("userRole"		,userRole);
		model.addAttribute("logStatus"		,logStatus);
		model.addAttribute("ownedGamesList"	,ownedGamesList);
		
		return "secure/ownedGames";
	}
	
}
