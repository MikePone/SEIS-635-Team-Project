package com.trl;


public class Textbook
{
	private final String textbookID;
	//TODO - rethink using double here.  Double is notoriously inaccurate after arithmetic is applied.
	//Maybe BigDecimal.
	private final double price;
	private final String ISBN;
	private final String author;
	private final String title;
	
	public Textbook(String id, double price, String ISBN, String author, String title) {
		super();
		if (id ==null || "".equals(id.trim())) {
			throw new IllegalArgumentException("Book ID cannot be empty");
		}
		this.textbookID = id;
		if (price <0) {
			throw new IllegalArgumentException("price must be 0, or a positive number");
		}
		this.price = price;
		if (ISBN ==null || "".equals(ISBN.trim())) {
			throw new IllegalArgumentException("ISBN cannot be empty");
		}
		this.ISBN=ISBN;
		if (author ==null || "".equals(author.trim())) {
			throw new IllegalArgumentException("Author cannot be empty");
		}
		this.author=author;
		if (title ==null || "".equals(title.trim())) {
			throw new IllegalArgumentException("Title cannot be empty");
		}
		this.title=title;
	}
	public String getBookID() {
		return textbookID;
	}
	public double getPrice() {
		return price;
	}
	public String getTextbookID() {
		return textbookID;
	}
	public String getISBN() {
		return ISBN;
	}
	public String getAuthor() {
		return author;
	}
	public String getTitle() {
		return title;
	}
	
}
