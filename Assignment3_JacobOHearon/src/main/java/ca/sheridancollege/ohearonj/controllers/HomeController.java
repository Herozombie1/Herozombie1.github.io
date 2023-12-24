package ca.sheridancollege.ohearonj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ca.sheridancollege.ohearonj.database.DatabaseAccess;

@Controller
public class HomeController
{	
	//Usual stuff
	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	//Return Index Page
	@GetMapping("/")
	public String index(Model model)
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
		
		return "index";
	}
			
	//Return permission denied page
	@GetMapping("/permission-denied")
	public String permissionDenied()
	{
		return "/error/permission-denied";
	}
}
