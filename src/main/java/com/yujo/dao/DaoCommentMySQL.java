package com.yujo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.yujo.model.Comment;
import com.yujo.model.Post;
import com.yujo.model.User;
import com.yujo.util.BasicDao;
import com.yujo.util.IMappable;

@Repository
public class DaoCommentMySQL extends BasicDao implements IDaoComment{

	private static final String SELECT_USERS = "SELECT * FROM users WHERE id = ?";
	private static final String SELECT__POSTS = "SELECT name, surname, posts.* FROM posts INNER JOIN users ON users.id = posts.id_user WHERE posts.id = ?";
	private static final String UPDATE_COMMENTS = "UPDATE comments SET content=?";
	private static final String WHERE_ID = " WHERE id=?";
	private static final String DELETE_COMMENTS = "DELETE FROM comments";
	private static final String INSERT_INTO_COMMENTS = "INSERT INTO comments (id_user, id_post, content) VALUES (?,?,?)";
	private static final String WHERE_ID_POST = " WHERE id_post = ?";
	private static final String SELECT_COMMENTS = "SELECT * FROM comments";

	public DaoCommentMySQL(
			@Value ("${db.address}") String dbAddress,
			@Value ("${db.user}") String user,
			@Value ("${db.psw}") String password){
		super(dbAddress, user, password);
	}

	private Comment setStuffInComment(int id_post, int id_user, Map<String, String> map) {
        Post p = IMappable.fromMap(Post.class, getOne(SELECT__POSTS, id_post));
        User u = IMappable.fromMap(User.class, getOne(SELECT_USERS, id_user));
        Comment c = IMappable.fromMap(Comment.class, map);
        String userImage = u.getImage();
        if ( userImage != null ) {
            u.setImage( "img/" + userImage );
        }
        c.setPost(p);
        c.setUser(u);
        return c;
    }

	
	 /**
     *This method will return a List of comments taking as parameter the id_post
     * @param id_post
     * @return List
     */
    @Override
    public List<Comment> comments(int id_post) {
        List<Comment> res = new ArrayList<>();
        List<Map<String, String>> maps = getAll(SELECT_COMMENTS+ WHERE_ID_POST, id_post);
        for (Map<String, String> map : maps) {
            int id_user = Integer.parseInt(map.get("id_user"));
            res.add(setStuffInComment(id_post, id_user, map));
        }
        return res;
    }

    @Override
    public Comment comment( int idComment ) {
        Map<String, String> map = getOne(SELECT_COMMENTS + WHERE_ID, idComment);
        if(map != null){
            int id_user = Integer.parseInt(map.get("id_user"));
            int id_post = Integer.parseInt(map.get("id_post"));
            return setStuffInComment(id_post, id_user, map);
        }
        return null;
    }

    /**
     *With this method we can add in our yujo db a new comments row
     * @param c
     * @return boolean
     */
    @Override
    public boolean add(Comment c) {
        return executeAndIsModified(INSERT_INTO_COMMENTS, c.getUser().getId(), c.getPost().getId(), c.getContent());
    }

    /**
     *With this method we can delete a comment row in our database
     * @param id
     * @return boolean
     */
    @Override
    public boolean delete(int id) {
        return executeAndIsModified(DELETE_COMMENTS+WHERE_ID,id);
    }

    /**
     *With this method we can modify a row in our db
     * @param c
     * @return boolean
     */
    @Override
    public boolean update(Comment c) {
        return executeAndIsModified(UPDATE_COMMENTS+WHERE_ID, c.getContent(), c.getId());
    }

}
