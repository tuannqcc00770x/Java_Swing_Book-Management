package com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.context.DBContext;
import com.entity.Users;

public class UsersDAO {

	//return the list of all users
    public List<Users> selectAll()throws Exception {
        String select = "select * from Users";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(select);
        ResultSet rs = ps.executeQuery();
        List<Users> users = new ArrayList<>();
        while (rs.next()) {
        	String username = rs.getString("username");
        	String displayName = rs.getString("displayname");
        	String password = rs.getString("password");
        	String description = rs.getString("description");
        	users.add(new Users(username, displayName, password, description));
        }
        rs.close();
        conn.close();
        return users;
    }
    
  //get a user by user name, return null if given user name does not exits
    public Users getUserByUsername(String username)throws Exception {
        String select = "select * from Users where username = ?";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(select);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        Users u= null;
        if (rs.next()) {
        	String displayName = rs.getString("displayName");
        	String password = rs.getString("password");
        	String description = rs.getString("description");
        	u = new Users(username, displayName, password, description);
        }
        rs.close();
        conn.close();
        return u;
    }
    
  //return true if a given user is valid (means exists in our system), otherwise return false
    public boolean validUser(Users x)throws Exception {
        List<Users> users = selectAll();
        for (Users u : users) {
        	if(u.getUsername().equalsIgnoreCase(x.getUsername()) && u.getPassword().equals(x.getPassword())) 
        		return true;
        }
        return false;
    }
    
    //insert new user to data
    public void addUser(Users u) throws Exception {
        String insert = "insert into Users(UserName,DisplayName,Password,Description) values(?, ?, ?, ?)";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setString(1, u.getUsername());
        ps.setString(2, "User");
        ps.setString(3, u.getPassword());
        ps.setString(4, "");
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    
    //return true if user is exist
    public boolean checkUser(Users x)throws Exception {
        List<Users> users = selectAll();
        for (Users u : users) {
        	if(u.getUsername().equalsIgnoreCase(x.getUsername())) 
        		return true;
        }
        return false;
    }
    
    //change info of user
    public void editUser(String oldName, Users u) throws Exception {
    	String insert = "Alter Table Books NOCHECK Constraint All; "
    			+ "update Users set UserName = ?, Password = ? where  Username = ?; "
    			+ "update Books set username = ? where  username = ?; "
    			+ "Alter Table Books CHECK Constraint ALL";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setString(1, u.getUsername());
        ps.setString(2, u.getPassword());
        ps.setString(3, oldName);
        ps.setString(4, u.getUsername());
        ps.setString(5, oldName);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
}
