package ca.sheridancollege.ohearonj.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.ohearonj.beans.VideoGame;
import ca.sheridancollege.ohearonj.beans.User;

@Repository
public class DatabaseAccess
{
	//Setting up the usual
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//================================================================================================//
	//																								  //
	//									Video game database control									  //
	//																								  //
	//================================================================================================//
	
	//Get all video games
	public List<VideoGame> getAllVideoGames()
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM VideoGames";
		
		return jdbc.query	(
								query,
								namedParameters,
								new BeanPropertyRowMapper<VideoGame>(VideoGame.class)
							);
	}
	
	//Get video game by ID
	public VideoGame getVideoGameById(Long videoGameId)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM VideoGames WHERE videoGameId = :videoGameId";
		
		namedParameters.addValue("videoGameId", videoGameId);
		
		return jdbc.queryForObject	(
										query,
										namedParameters,
										new BeanPropertyRowMapper<VideoGame>(VideoGame.class)
									);
	}
	
	//Get video game by name
	public VideoGame getVideoGameByName(String title)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM VideoGames WHERE title = :title";
		
		namedParameters.addValue("title", title);
		
		return jdbc.queryForObject	(
										query,
										namedParameters,
										new BeanPropertyRowMapper<VideoGame>(VideoGame.class)
									);
	}
	
	//Insert video game
	public void insertVideoGame(VideoGame videoGame)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query 	= "INSERT INTO VideoGames(title, publisher, price, description, size, microtransactions)"
						+ "VALUES (:title, :publisher, :price, :description, :size, :microtransactions)";
		
		namedParameters.addValue("title"			, videoGame.getTitle());
		namedParameters.addValue("publisher"		, videoGame.getPublisher());
		namedParameters.addValue("price"			, videoGame.getPrice());
		namedParameters.addValue("description"		, videoGame.getDescription());
		namedParameters.addValue("size"				, videoGame.getSize());
		namedParameters.addValue("microtransactions", videoGame.getMicrotransactions());
		
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if(rowsAffected > 0)
		{
			System.out.println("Added Video Game [ " + videoGame.getTitle() + " ] to system");
		}
	}
	
	//Edit video game
	public void editVideoGame(VideoGame updatedVideoGame)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query 	= "UPDATE VideoGames SET title = :title, publisher = :publisher, price = :price, description = :description, size = :size, microtransactions = :microtransactions "
						+ "WHERE videoGameId = :videoGameId";
		
		namedParameters.addValue("videoGameId"			, updatedVideoGame.getVideoGameId());
		namedParameters.addValue("title"				, updatedVideoGame.getTitle());
		namedParameters.addValue("publisher"			, updatedVideoGame.getPublisher());
		namedParameters.addValue("price"				, updatedVideoGame.getPrice());
		namedParameters.addValue("description"			, updatedVideoGame.getDescription());
		namedParameters.addValue("size"					, updatedVideoGame.getSize());
		namedParameters.addValue("microtransactions"	, updatedVideoGame.getMicrotransactions());

		
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if(rowsAffected > 0)
		{
			System.out.println("Edited Game [ " + updatedVideoGame.getTitle() + " ] in system");
		}
	}
	
	//Delete video game
	public void deleteVideoGame(Long videoGameId)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query	= "DELETE FROM VideoGames WHERE videoGameId = :videoGameId;"
						+ "DELETE FROM OwnedGames WHERE videoGameId = :videoGameId;";
		
		namedParameters.addValue("videoGameId", videoGameId);
		
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if(rowsAffected > 0)
		{
			System.out.println("Deleted [ " + videoGameId + " ] from system");
		}
	}
	
	
	
	//================================================================================================//
	//																								  //
	//									User based database control									  //
	//																								  //
	//================================================================================================//
	
	//Get user by ID
	public User getUserById(Long userId)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM Users WHERE userId = :userId";
		
		namedParameters.addValue("userId", userId);
		
		return jdbc.queryForObject	(
										query,
										namedParameters,
										new BeanPropertyRowMapper<User>(User.class)
									);
	}
	
	//Get user by Name
	public User getUserByName(String username)
	{		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM Users WHERE username = :username OR email = :username";
		
		namedParameters.addValue("username", username);
		
		return jdbc.queryForObject	(
										query,
										namedParameters,
										new BeanPropertyRowMapper<User>(User.class)
									);
		
	}
	
	//Edit user
	public void editUser(User user)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query 	= "UPDATE Users SET username = :username, email = :email, password = :password "
						+ "WHERE userId = :userId";
			
		namedParameters.addValue("userId"	, user.getUserId());
		namedParameters.addValue("username"	, user.getUsername());
		namedParameters.addValue("email"	, user.getEmail());
		namedParameters.addValue("password" , user.getPassword());
		
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if(rowsAffected > 0)
		{
			System.out.println("Edited User [ " + user.getUsername() + " ] in system");
		}
	}
		
	//Delete user
	public void deleteUser(Long userId)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query	= "DELETE FROM UserRoles WHERE userId = :userId;"
						+ "DELETE FROM OwnedGames WHERE userId = :userId;"
						+ "DELETE FROM Users WHERE userId = :userId;";
		
		namedParameters.addValue("userId", userId);
		
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if(rowsAffected > 0)
		{
			System.out.println("Deleted user [ " + userId + " ] from system");
		}
	}
	
	
	
	
	
	//================================================================================================//
	//																								  //
	//										Purchase Based Controls									  //
	//																								  //
	//================================================================================================//
	
	//Purchase Item in Cart
	public void purchaseItemInCart(Long userId, Long videoGameId)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query 	= "INSERT INTO OwnedGames(userId, videoGameId)"
						+ "VALUES(:userId, :videoGameId)";
		
		namedParameters.addValue("userId"		, userId);
		namedParameters.addValue("videoGameId"	, videoGameId);
		
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if(rowsAffected > 0)
		{
			System.out.println("Video Game :videoGameId added to :userId 's library");
		}
	}
	
	//================================================================================================//
	//																								  //
	//										Owned Games Based Controls								  //
	//																								  //
	//================================================================================================//
	
	//View Games Owned by User based on Id
	public List<VideoGame> getOwnedGamesByUserId(Long userId)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM OwnedGames WHERE userId = :userId";
		
		namedParameters.addValue("userId", userId);
		
		return jdbc.query	(
								query,
								namedParameters,
								new BeanPropertyRowMapper<VideoGame>(VideoGame.class)
							);
	}
	
	//Check to see if user already owns the game
	public boolean checkIfUserOwnsGame(Long userId, Long videoGameId)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT COUNT(*) FROM OwnedGames WHERE userId = :userId AND videoGameId = :videoGameId";
		
		namedParameters.addValue("userId"		, userId);
		namedParameters.addValue("videoGameId"	, videoGameId);
		
		int count = jdbc.queryForObject(query, namedParameters, Integer.class);

	    return count > 0;
	}
	
	//================================================================================================//
	//																								  //
	//								Database control for security functions							  //
	//																								  //
	//================================================================================================//
	
	//Add user to secondary table
	public void addUser(String username, String email, String password)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query 	= "INSERT INTO Users "
						+ "(username, email ,password) "
						+ "VALUES (:username, :email, :password)";
		
		namedParameters.addValue("username", username);
		namedParameters.addValue("email", email);
		namedParameters.addValue("password", passwordEncoder.encode(password));
		
		jdbc.update(query, namedParameters);
	}
	
	//Give user a security role
	public void addRole(Long userId, Long roleId)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query 	= "INSERT INTO UserRoles (userId, roleId) "
						+ "VALUES (:userId, :roleId)";
		
		namedParameters.addValue("userId", userId);
		namedParameters.addValue("roleId", roleId);
		
		jdbc.update(query, namedParameters);
	}
	
	//Get User roles by Id
	public List<String> getRolesById(Long userId)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT Roles.roleName "
					+ "FROM UserRoles, Roles "
					+ "WHERE UserRoles.roleId = Roles.roleId "
					+ "AND userId = :userId";
		
		namedParameters.addValue("userId", userId);
		
		return jdbc.queryForList(query,  namedParameters, String.class);
	}	
	
}
