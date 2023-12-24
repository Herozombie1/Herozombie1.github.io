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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.ohearonj.beans.User;
import ca.sheridancollege.ohearonj.beans.VideoGame;
import ca.sheridancollege.ohearonj.database.DatabaseAccess;

@Controller
public class UserController
{
	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	List<String> userRoles = new CopyOnWriteArrayList<>();
	
	//================================================================================================//
	//																								  //
	//										Account Setting Functions								  //
	//																								  //
	//================================================================================================//
	//Return login page
	@GetMapping("/login")
	public String login(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String currentUsername = "";
		Boolean logStatus = null;
		
		try
		{
			 currentUsername = da.getUserByName(authentication.getName()).getUsername();
			 logStatus = true;
		}
		catch(Exception e)
		{
			currentUsername = "None";
			logStatus = false;
		}
		
		model.addAttribute("username"    	,currentUsername);
		model.addAttribute("logStatus"		,logStatus);
		
		return "login";
	}
		
	//Return Register Page
	@GetMapping("/register")
	public String register(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String currentUsername = "";
		Boolean logStatus = null;
		
		try
		{
			 currentUsername = da.getUserByName(authentication.getName()).getUsername();
			 logStatus = true;
		}
		catch(Exception e)
		{
			currentUsername = "None";
			logStatus = false;
		}
		
		model.addAttribute("username"  		,currentUsername);
		model.addAttribute("logStatus"		,logStatus);
		
		return "register";
	}
		
	//Process register request and return to index
	@PostMapping("/register")
	public String postRegister(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model)
	{
		da.addUser(username, email, password);
		Long userId = da.getUserByName(username).getUserId();
					
		da.addRole(userId, Long.valueOf(1));
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String currentUsername = "";
		Boolean logStatus = null;
		
		try
		{
			 currentUsername = da.getUserByName(authentication.getName()).getUsername();
			 logStatus = true;
		}
		catch(Exception e)
		{
			currentUsername = "None";
			logStatus = false;
		}
		
		model.addAttribute("username"    	,currentUsername);
		model.addAttribute("logStatus"		,logStatus);
				
		return "index";
	}
	
	@GetMapping("/viewProfile")
	public String viewProfile(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String currentUsername = "";
		Boolean logStatus = null;
		User profile = new User();
		
	
	
		currentUsername = da.getUserByName(authentication.getName()).getUsername();
		logStatus = true;
		profile = da.getUserByName(currentUsername);
		
		
		model.addAttribute("username"  		,currentUsername);
		model.addAttribute("logStatus"		,logStatus);
		model.addAttribute("profile"		,profile);
		model.addAttribute("user" 			,profile);
		
		return "secure/profile";
	}
	
	@PostMapping("/editUser/{userId}")
	public String editBook(Model model, @ModelAttribute User user)
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
		
		da.editUser(user);
		
		model.addAttribute("user"		,user);
		model.addAttribute("username"   ,currentUsername);
		model.addAttribute("userRole"	,userRole);
		model.addAttribute("logStatus"	,logStatus);
		model.addAttribute("videoGame"		,new VideoGame());
		model.addAttribute("videoGameList"	,da.getAllVideoGames());
		
		return "browseGames";
	}
	
	@GetMapping("/deleteUser")
	public String deleteUser(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String currentUser = "";
		User profile = new User();
		Long userId = null;
		
		try
		{
			 currentUser = da.getUserByName(authentication.getName()).getUsername();
			 profile = da.getUserByName(currentUser);
			 userId = profile.getUserId();
		}
		catch(Exception e)
		{
			currentUser = "None";
		}
		
		da.deleteUser(userId);
		
		model.addAttribute("username"  		,"None");
		model.addAttribute("logStatus"		,false);
		model.addAttribute("profile"		,profile);
		
		return "index";
	}
}
