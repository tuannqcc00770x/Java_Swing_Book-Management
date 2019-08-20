package com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.context.DBContext;
import com.entity.Publisher;

public class PublisherDAO {
    //return the list of all publishers
    public List<Publisher> selectAll()throws Exception {
        String select = "select * from publishers";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(select);
        ResultSet rs = ps.executeQuery();
        List<Publisher> publishers = new ArrayList<>();
        while (rs.next()) {
        	String id = rs.getString("pub_id");
        	String name = rs.getString("pub_name");
        	String address = rs.getString("pub_address");
        	publishers.add(new Publisher(id, name, address));
        }
        rs.close();
        return publishers;
    }
    //return information of a Publisher by publisher id, 
    //return null if a given publisher id does not exists
    public Publisher getPublisherByID(String pid) throws Exception {
        List<Publisher> publishers = selectAll();
        for (Publisher p : publishers) {
			if(p.getId().equalsIgnoreCase(pid)) return p;
		}
        return null;
    }

    //insert new publisher
    public void addPub(Publisher p) throws Exception {
        String insert = "insert into Publishers(pub_id, pub_name, pub_address) values(?, ?, ?)";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setString(1, p.getId());
        ps.setString(2, p.getName());
        ps.setString(3, p.getAddress());
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    
    //return true if pub id is exist
    public boolean checkPub(Publisher x)throws Exception {
        List<Publisher> pubs = selectAll();
        for (Publisher p : pubs) {
        	System.out.println(p.getId());
        	if(p.getId().equalsIgnoreCase(x.getId())) {
        		return true;
        	}
        }
        return false;
    }
}
