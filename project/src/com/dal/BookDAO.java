package com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.context.DBContext;
import com.entity.Author;
import com.entity.Book;
import com.entity.Publisher;

public class BookDAO {
	
	 //delete a book by a given bookID
    public void deleteBook(String bookID) throws Exception {
        String delete = "delete from titleauthor where title_id = ?";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(delete);
        ps.setString(1, bookID);
        ps.executeUpdate();
        ps.close();
        delete = "delete from Books where title_id = ?";
        ps = conn.prepareStatement(delete);
        ps.setString(1, bookID);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    
    //update a new book to database
    public void editBook(Book b) throws Exception {
        String update = "update Books set title = ?, pub_id = ?, notes = ? where title_id = ?";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(update);
        ps.setString(1, b.getTitle());
        ps.setString(2, b.getPub().getId());
        ps.setString(3, b.getNote());
        ps.setString(4, b.getId());
        ps.executeUpdate();
        ps.close();
        String delete = "delete from TitleAuthor where title_id = ?";
        ps = conn.prepareStatement(delete);
        ps.setString(1, b.getId());
        ps.executeUpdate();
        ps.close();
        List<Author> authors = b.getAuthors();
        for (int i=0; i<authors.size(); i++) {
        	Author a = authors.get(i);
        	String insert = "insert into TitleAuthor values(?, ?, ?)";
        	PreparedStatement p = conn.prepareStatement(insert);
        	p.setString(1, a.getId());
        	p.setString(2, b.getId());
        	p.setInt(3, i);
        	p.executeUpdate();
        	p.close();
        }
        conn.close();
    }
    
    
    //insert a new book to database
    public void addBook(Book b) throws Exception {
        String insert = "insert into Books values(?, ?, ?, ?, ?)";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setString(1, b.getId());
        ps.setString(2, b.getTitle());
        ps.setString(3, b.getPub().getId());
        ps.setString(4, b.getNote());
        ps.setString(5, b.getUser().getUsername());
        ps.executeUpdate();
        ps.close();
        List<Author> authors = b.getAuthors();
        for (int i=0; i<authors.size(); i++) {
        	Author a = authors.get(i);
        	insert = "insert into TitleAuthor values(?, ?, ?)";
        	PreparedStatement p = conn.prepareStatement(insert);
        	p.setString(1, a.getId());
        	p.setString(2, b.getId());
        	p.setInt(3, i);
        	p.executeUpdate();
        	p.close();
        }
        conn.close();
    }
    
    //return information of a Book of a given bookID
    //return null if a given bookID does not exists
    public Book getBookByBookID(String bookID)throws Exception {
        String select = "select * from Books where title_id = ?";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(select);
        ps.setString(1, bookID);
        ResultSet rs = ps.executeQuery();
        PublisherDAO pubDAO = new PublisherDAO();
        AuthorDAO authorDAO = new AuthorDAO();
        Book b = null;
        if (rs.next()) {
        	String id = rs.getString("title_id");
        	String title = rs.getString("title");
        	String pudID = rs.getString("pub_id");
        	String note = rs.getString("notes");
        	Publisher pub = pubDAO.getPublisherByID(pudID);
        	List<Author> authors = authorDAO.selectAuthorsByBookID(id);
        	b = new Book(id, title, note, pub, authors);
        }
        rs.close();
        conn.close();
        return b;
    }
    //return the list of books - use for searching, need to join all given tables except Users
    public List<Book> select(String columnName, String keyword)throws Exception {
        String select = "select distinct Books.* from Books, Publishers, Authors, TitleAuthor where " +
        		" books.pub_id = Publishers.pub_id and books.title_id = TitleAuthor.title_id and TitleAuthor.au_id = Authors.au_id and ";
        select += columnName + " like '%" + keyword +"%'";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(select);
        ResultSet rs = ps.executeQuery();
        List<Book> books = new ArrayList<>();
        PublisherDAO pubDAO = new PublisherDAO();
        AuthorDAO authorDAO = new AuthorDAO();
        UsersDAO usersDAO = new UsersDAO();
        while (rs.next()) {
        	String id = rs.getString("title_id");
        	String title = rs.getString("title");
        	String pudID = rs.getString("pub_id");
        	String note = rs.getString("notes");
        	String username = rs.getString("username");
        	Publisher pub = pubDAO.getPublisherByID(pudID);
        	List<Author> authors = authorDAO.selectAuthorsByBookID(id);
        	books.add(new Book(id, title, note, pub, authors, usersDAO.getUserByUsername(username)));
        }
        rs.close();
        conn.close();
        return books;
    }
    
    //return the list of all books
    public List<Book> selectAll()throws Exception {
       String select = "select * from Books";
       Connection conn = new DBContext().getConnection();
       PreparedStatement ps = conn.prepareStatement(select);
       ResultSet rs = ps.executeQuery();
       List<Book> books = new ArrayList<>();
       PublisherDAO pubDAO = new PublisherDAO();
       AuthorDAO authorDAO = new AuthorDAO();
       UsersDAO usersDAO = new UsersDAO();
       while (rs.next()) {
       	String id = rs.getString("title_id");
       	String title = rs.getString("title");
       	String pudID = rs.getString("pub_id");
       	String note = rs.getString("notes");
       	String username = rs.getString("username");
       	Publisher pub = pubDAO.getPublisherByID(pudID);
       	List<Author> authors = authorDAO.selectAuthorsByBookID(id);
       	books.add(new Book(id, title, note, pub, authors, usersDAO.getUserByUsername(username)));
       }
       rs.close();
       conn.close();
       return books;
    }

}
