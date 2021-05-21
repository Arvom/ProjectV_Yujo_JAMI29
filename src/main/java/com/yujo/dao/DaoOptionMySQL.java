package com.yujo.dao;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.yujo.model.Option;
import com.yujo.model.Post;
import com.yujo.model.User;
import com.yujo.util.BasicDao;
import com.yujo.util.IMappable;

/**
 * This class manages all the calls to the database yujo 
 * in particular to the table options
 */

@Repository
public class DaoOptionMySQL extends BasicDao implements IDaoOption {

	private static final String SELECT_USER = "SELECT * FROM users";
	private static final String UPDATE_OPTION_ID_USERS = "UPDATE options SET id_users = ?, vote= ?";
	private static final String UPDATE_OPTIONS_CONTENT = "UPDATE options SET content=?, id_users='', vote=0";
	private static final String DELETE_OPTIONS = "DELETE FROM options";
	private static final String INSERT_INTO_OPTIONS = "INSERT INTO options(id_post, content, id_user, id_users, vote) VALUES (?,?,?,?, 1)";
	private static final String WHERE_ID = " WHERE id = ?";
	private static final String SELECT_POST = "SELECT * FROM posts";
	private static final String WHERE_ID_POST = " WHERE id_post = ?";
	private static final String SELECT_OPTIONS = "SELECT * FROM options";

	/**
     * This constructor get the value of dbAddress, user and password
     * from the application.properties and allows the
     * connection to the yujo database.
     * @param dbAddress
     * @param user
     * @param password
     */
	public DaoOptionMySQL(
			@Value ("${db.address}") String dbAddress,
			@Value ("${db.user}") String user,
			@Value ("${db.psw}") String password) {
		super(dbAddress, user, password);
	}

	/**
	 * this method send to the database a query that select all the data from the table options
	 * associate to a post and transform them in the object option.
	 * It also create the objects post and user for each option.
	 * 
	 * @param id_post
	 * @return the list of all the option below a post/poll
	 */
	
	@Override
	public List<Option> options(int id_post) {
		List<Option> res = new ArrayList<>();
		List<Map<String,String>> maps = getAll(SELECT_OPTIONS + WHERE_ID_POST, id_post);
		
		Map<String,String> post = getOne(SELECT_POST + WHERE_ID, id_post);
		Post p = IMappable.fromMap(Post.class, post);
		
		for (Map<String, String> map : maps) {
			
			Option option = IMappable.fromMap(Option.class, map);
			option.setPost(p);
			
			int id_user = Integer.parseInt(map.get("id_user"));
			Map <String, String> user = getOne(SELECT_USER + WHERE_ID, id_user);
			User u = IMappable.fromMap(User.class, user);
			option.setUser(u);
			
			res.add(option);
		}
		return res;
	}

	
	/**
	 * this method insert a new option in the database
	 * 
	 * @param <b>Option o</b> the new option to insert in the database
	 * @return true if the data's been uploaded into the table options
	 */
	@Override
	public boolean add(Option o) {		
		return executeAndIsModified(INSERT_INTO_OPTIONS, o.getPost().getId(),
				o.getContent(), o.getUser().getId(), o.getUser().getId());
	}

	/**
	 * this method update the data from an option in the database
	 * 
	 * @param <b>Option o</b> already existing option to update in the database
	 * @return true if the data's been uploaded into the table options
	 */
	@Override
	public boolean update(Option o) {
		return executeAndIsModified(UPDATE_OPTIONS_CONTENT+ WHERE_ID, o.getContent(), o.getId());
	}
	
	/**
	 * this method remove an option from the database
	 * 
	 * @param <b>int id</b> the id that identify the option to delete from the database
	 * @return true if the row is been removed from the table
	 */
	@Override
	public boolean delete(int id) {
		return executeAndIsModified(DELETE_OPTIONS+ WHERE_ID,id);
	}
	
	/**
	 * this method insert a new vote for an option
	 * take the id of the user that has voted and if the id 
	 * is not already registered add it in the field "id_users"
	 *  
	 * @param <b>Option o</b> the option that has received a new vote
	 * 		  <b>int id</b> the id of the user that had voted
	 * @return true if the id of the user is been registered
	 */
	@Override
	public boolean add(Option o, int id) {
		
		String []temp = o.getId_users().split("@");
		boolean hasAlreadyVoted = false;
		
		for (String string : temp) {
			int user_id = Integer.parseInt(string);
			if (id == user_id) {
				hasAlreadyVoted = true;
			}
		}
		
		if(hasAlreadyVoted) {
			return false;
		}
		o.setId_users(o.getId_users()+"@"+id);
		return executeAndIsModified(UPDATE_OPTION_ID_USERS + WHERE_ID,o.getId_users(), o.getVote()+1, o.getId());
	}
}