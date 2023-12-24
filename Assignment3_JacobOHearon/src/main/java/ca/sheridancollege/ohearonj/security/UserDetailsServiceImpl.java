package ca.sheridancollege.ohearonj.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.ohearonj.database.DatabaseAccess;
import ca.sheridancollege.ohearonj.beans.User;



@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	@Override
	public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException
	{
		User user = da.getUserByName(username);
		
		//If the user doesn't exist, throw an exception
		if (user == null)
		{
			System.out.println("User not found:" + username);
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
		
		List<String> roleNameList = da.getRolesById(user.getUserId());
		
		List<GrantedAuthority> grantList = new ArrayList<>();
		if (roleNameList != null)
		{
			for(String role : roleNameList)
			{
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		
		//Convert custom user bean into spring boot user details
		String password = user.getPassword();
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				username, password ,grantList);
		
		return userDetails;
	}
}
