package com.trl;

import java.math.BigDecimal;

public class Textbook
{
	private final String textbookID;
	private final BigDecimal price;
	private final String ISBN;
	private final String author;
	private final String title;
	private final String edition;

	public Textbook(String id, BigDecimal price, String ISBN, String author, String title, String edition) {
		super();
		if (id ==null || "".equals(id.trim())) {
			throw new IllegalArgumentException("Book ID cannot be empty");
		}
		this.textbookID = id;
		if (price.compareTo(BigDecimal.ZERO) < 0 ) {
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
		//edition can be null
		this.edition=edition;
	}
	
	public String getBookID() {
		return textbookID;
	}
	public BigDecimal getPrice() {
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
	
	public String getEdition() {
		return edition;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ISBN == null) ? 0 : ISBN.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((textbookID == null) ? 0 : textbookID.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Textbook other = (Textbook) obj;
		if (ISBN == null) {
			if (other.ISBN != null)
				return false;
		} else if (!ISBN.equals(other.ISBN))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (textbookID == null) {
			if (other.textbookID != null)
				return false;
		} else if (!textbookID.equals(other.textbookID))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
}
