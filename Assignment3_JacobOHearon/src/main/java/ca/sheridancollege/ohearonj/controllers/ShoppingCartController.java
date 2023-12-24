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
import org.springframework.web.bind.annotation.PathVariable;

import ca.sheridancollege.ohearonj.beans.VideoGame;
import ca.sheridancollege.ohearonj.database.DatabaseAccess;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShoppingCartController
{
	//The usual
	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	List<String> userRoles = new CopyOnWriteArrayList<>();
	
	//================================================================================================//
	//																								  //
	//									Shopping Cart Functionality									  //
	//																								  //
	//================================================================================================//

	List<VideoGame> shoppingCart = new CopyOnWriteArrayList<VideoGame>();
		
	@GetMapping("/addToCart/{videoGameId}")
	public String addToCart(Model model, @PathVariable Long videoGameId, HttpSession session)
	{
		VideoGame videoGame = da.getVideoGameById(videoGameId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String currentUsername = "";
		Boolean logStatus = null;
		String userRole = "";
		Long userId = null;
		
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
		
		if(session.isNew())
		{
			shoppingCart.add(videoGame);
			
			session.setAttribute("shoppingCart", shoppingCart);
			
			System.out.println("Shopping Cart!");
		}
		else
		{
			session.getAttribute("shoppingCart");
			
			shoppingCart.add(videoGame);
			
			session.setAttribute("shoppingCart", shoppingCart);
		}
		
		model.addAttribute("username"  		,currentUsername);
		model.addAttribute("userRole"		,userRole);
		model.addAttribute("logStatus"		,logStatus);
		model.addAttribute("videoGame"		,new VideoGame());
		model.addAttribute("videoGameList"	,da.getAllVideoGames());
		
		return "browseGames";
	}
	
	@GetMapping("/viewShoppingCart")
	public String viewShoppingCart(Model model, HttpSession session)
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
		model.addAttribute("videoGame"		,new VideoGame());
		model.addAttribute("shoppingCart"	,shoppingCart);
		
		return "secure/shoppingCart";
	}
	
	@GetMapping("/removeFromCart/{videoGameId}")
	public String removeFromCart(Model model, HttpSession session, @PathVariable Long videoGameId)
	{	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String currentUsername = "";
		Boolean logStatus = null;
		String userRole = "";
		
		try
		{
			 currentUsername = da.getUserByName(authentication.getName()).getUsername();
			 userRole = userRoles.isEmpty() ? "No Role" : userRoles.get(0);
			 logStatus = true;
		}
		catch(Exception e)
		{
			currentUsername = "None";
			logStatus = false;
		}
		
		VideoGame videoGame = da.getVideoGameById(videoGameId);
		
		session.getAttribute("shoppingCart");
		
		shoppingCart.remove(videoGame);
		
		session.setAttribute("shoppingCart", shoppingCart);
		
		model.addAttribute("username"  		,currentUsername);
		model.addAttribute("userRole"		,userRole);
		model.addAttribute("logStatus"		,logStatus);
		model.addAttribute("book", new VideoGame());
		model.addAttribute("shoppingCart", shoppingCart);
		
		return "secure/shoppingCart";
	}
	
	@GetMapping("/purchaseItemsInCart")
	public String purchaseItemsInCard(Model model, HttpSession session)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String currentUsername = da.getUserByName(authentication.getName()).getUsername();
		Boolean logStatus = true;
		long userId = da.getUserByName(currentUsername).getUserId();
		
		session.getAttribute("shoppingCart");
		
		for(VideoGame videoGame : shoppingCart)
		{
			long videoGameId = videoGame.getVideoGameId();
			
			boolean ownedGame = da.checkIfUserOwnsGame(userId, videoGameId);
			
			if(ownedGame == false)
			{
				da.purchaseItemInCart(userId, videoGameId);
			}
			else
			{
				System.out.println("This user already owns this game: " + videoGameId);
			}
		}
		
		session.getAttribute("shoppingCart");
		shoppingCart.clear();
		
		model.addAttribute("username"  		,currentUsername);
		model.addAttribute("logStatus"		,logStatus);
		model.addAttribute("book", new VideoGame());
		model.addAttribute("shoppingCart", shoppingCart);
		
		return "secure/confirmedPurchase";
	}
}