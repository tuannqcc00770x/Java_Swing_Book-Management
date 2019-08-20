package com.controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.dal.BookDAO;
import com.entity.Book;

public class BookController {
	 private BookDAO bookDAO;
	    
	    //constructor
	    public BookController() {
	        bookDAO = new BookDAO();
	    }
	    
	    //add a new book to database
	    public void add(Book b) throws Exception {
	        bookDAO.addBook(b);
	    }
	    
	    //output list of all available books to jtable
	    public void list(JTable tblBook) throws Exception {
	        DefaultTableModel model = (DefaultTableModel) tblBook.getModel();
	        model.setRowCount(0);
	        List<Book> books = bookDAO.selectAll();
	        for (Book b : books) {
				model.addRow(b.toDataRow());
			}
	    }
	    
	    //search and output the list of all availables books to jtable
	    public void search(String column, String keyword,JTable tblBook) throws Exception {
	    	DefaultTableModel model = (DefaultTableModel) tblBook.getModel();
	        model.setRowCount(0);
	        List<Book> books = bookDAO.select(column, keyword);
	        for (Book b : books) {
				model.addRow(b.toDataRow());
			}
	    }
	    
	    //return information of a Book by Book ID
	    public Book getBookByBookID(String bookID)throws Exception {
	        return bookDAO.getBookByBookID(bookID);
	    }
	    
	    //update information of a Book
	    public void editBook(Book b)throws Exception  {
	        bookDAO.editBook(b);
	    }
	    
	    //delete a book by BookID
	    public void deleteBook(String bookID)throws Exception  {
	        bookDAO.deleteBook(bookID);
	    }
}
