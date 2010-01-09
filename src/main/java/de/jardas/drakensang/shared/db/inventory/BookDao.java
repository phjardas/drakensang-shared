package de.jardas.drakensang.shared.db.inventory;

import de.jardas.drakensang.shared.model.inventory.Book;

public class BookDao extends InventoryItemDao<Book> {
	public BookDao() {
		super(Book.class, "Book");
	}
}
