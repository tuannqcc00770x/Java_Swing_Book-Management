package com.entity;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id, title, note;
	    private Publisher pub;
	    //a list of Authors
	    private List<Author> authors;
	    private Users user;
	    

	    //convert a Book to a data row = an array of objects
	    public Object[] toDataRow() {
	        if (authors.size() > 0) {
	            String author = authors.get(0).getName();
	            if (authors.size() > 1) {
	                author += "...";
	            }
	            return new Object[]{id, title, pub.getName(), author, note, user.getUsername()};
	        }
	        return null;
	    }

	    public Book() {
	    }

	    public Book(String id, String title, String note, Publisher pub, List<Author> authors) {
	        this.id = id;
	        this.title = title;
	        this.note = note;
	        this.pub = pub;
	        this.authors = authors;
	    }

	    public Book(String id, String title, String note, Publisher pub, List<Author> authors, Users user) {
	        this.id = id;
	        this.title = title;
	        this.note = note;
	        this.pub = pub;
	        this.authors = authors;
	        this.user = user;
	    }

	    
	    
	    public Book(String id, String title, Publisher pub, String note) {
	        this.id = id;
	        this.title = title;
	        this.pub = pub;
	        this.note = note;
	    }

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public Publisher getPub() {
	        return pub;
	    }

	    public void setPub(Publisher pub) {
	        this.pub = pub;
	    }

	    public String getNote() {
	        return note;
	    }

	    public void setNote(String note) {
	        this.note = note;
	    }

	    public List<Author> getAuthors() {
	        return authors;
	    }

	    public void setAuthors(List<Author> authors) {
	        this.authors = authors;
	    }

	    public Users getUser() {
	        return user;
	    }

	    public void setUser(Users user) {
	        this.user = user;
	    }
	
}
