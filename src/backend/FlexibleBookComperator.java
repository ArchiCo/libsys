package backend;

import java.util.Comparator;

public class FlexibleBookComperator implements Comparator<Book> {
	public enum Order {
		Name, Author, Popularity
	}

	private Order sortingBy = Name;

	@Override
	public int compare(Book book1, Book book2) {
		switch (sortingBy) {
		case Name:
			return book1.getName().compareTo(book2.getName());
			break;
		case Author:
			return book1.getAuthor().compareTo(book2.getAuthor());
			break;
		case Popularity: {
			return book1.getLentTimes() < book2.getLentTimes() ? -1
					: book1.getLentTimes() == book2.getLentTimes() ? 0 : 1;
			break;
		}
		}
	}

	public void setSortingBy(Order sortingBy) {
		this.sortingBy = sortingBy;
	}
}
