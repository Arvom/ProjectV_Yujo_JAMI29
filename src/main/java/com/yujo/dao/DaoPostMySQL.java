package com.yujo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.yujo.model.Post;
import com.yujo.model.User;
import com.yujo.util.BasicDao;
import com.yujo.util.IMappable;

/**
 * This class manages all the calls to the database yujo
 * in particular to the table posts
 */
@Repository
public class DaoPostMySQL extends BasicDao implements IDaoPost {


    private static final String SELECT_USERS = "SELECT * FROM users";
    private static final String SELECT_POSTS = "SELECT * FROM posts";
    private static final String UPDATE_POST = "UPDATE posts SET content = ?";
    private static final String DELETE_POST = "DELETE FROM posts";
    private static final String INSERT_INTO_POSTS = "INSERT INTO posts (id_user, content, image) values (?,?,?)";
    private static final String DESC = " ORDER BY content_time DESC";
    private static final String WHERE_ID = " WHERE id=?";
    private static final String WHERE_ID_USER = " WHERE id_user=?";

    /**
     * This constructor get the value of dbAddress, user and password
     * from the application.properties and allows the
     * connection to the yujo database.
     *
     * @param dbAddress
     * @param user
     * @param password
     */
    public DaoPostMySQL(
            @Value("${db.address}") String dbAddress,
            @Value("${db.user}") String user,
            @Value("${db.psw}") String password ) {
        super( dbAddress, user, password );
    }

    /**
     * this methos take a Map that contains all the data of a post
     * create the object post and set the corresponding users
     *
     * @param map Map<String, String> map
     * @return the object Post whit the User already set
     */
    private Post setUserInPost( Map<String, String> map ) {
        int id_user = Integer.parseInt( map.get( "id_user" ) );
        User u = IMappable.fromMap( User.class, getOne( SELECT_USERS + WHERE_ID, id_user ) );
        String userImage = u.getImage();
        if ( userImage != null ) {
            u.setImage( "img/" + userImage );
        }
        Post p = new Post();
        p.fromMap( map );
        p.setUser( u );
        String image = p.getImage();
        if ( image != null ) {
            p.setImage( "img/" + image );
        }
        return p;
    }

    /**
     * this method send to the database a query that select all the data from the table posts
     * and transform them in the object post.
     *
     * @return the list of all the post
     */
    @Override
    public List<Post> posts() {
        List<Post> ris = new ArrayList<>();
        List<Map<String, String>> maps = getAll( SELECT_POSTS + DESC );
        for ( Map<String, String> map : maps ) {
            Post p = setUserInPost( map );
            ris.add( p );
        }
        return ris;
    }


    /**
     * this method send to the database a query that select
     * all the data from the table posts
     * where the field id_user correspond yo the id_user (param)
     * and transform them in the object post.
     *
     * @param id_user int id </b> the id of the post
     * @return an object Post
     */
    @Override
    public List<Post> posts( int id_user ) {
        List<Post> ris = new ArrayList<>();
        List<Map<String, String>> maps = getAll( SELECT_POSTS + WHERE_ID_USER + DESC, id_user );
        for ( Map<String, String> map : maps ) {
            Post p = setUserInPost( map );
            ris.add( p );
        }
        return ris;
    }

    // TODO JAVADOC
    @Override
    public Post post( int id_post ) {
        Map<String, String> map = getOne( SELECT_POSTS + WHERE_ID, id_post );
        if ( map != null ) {
            return setUserInPost( map );
        }
        return null;
    }


    /**
     * this method insert a new post in the database
     *
     * @param p Post p</b> the new post to insert in the database
     * @return true if the data's been uploaded into the table posts
     */
    @Override
    public boolean add( Post p ) {
        return executeAndIsModified( INSERT_INTO_POSTS, p.getUser().getId(), p.getContent(), p.getImage() );
    }

    /**
     * this method remove a post from the database
     *
     * @param id </b> the id that identify the post to delete from the database
     * @return true if the row is been removed from the table
     */
    @Override
    public boolean delete( int id ) {
        return executeAndIsModified( DELETE_POST + WHERE_ID, id );
    }


    /**
     * this method update the data from a post in the database
     *
     * @param p </b> already existing post to update in the database
     * @return true if the data's been uploaded into the table posts
     */
    @Override
    public boolean update( Post p ) {
        return executeAndIsModified( UPDATE_POST + WHERE_ID, p.getContent(), p.getId() );
    }


}
