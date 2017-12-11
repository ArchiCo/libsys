package backend;

import java.util.Comparator;

public class FlexibleBookComparator implements Comparator<Book> {
	public enum Order {
		Name, Author, Popularity
	}

	private Order sortingBy = Order.Name;

	@Override
	public int compare(Book book1, Book book2) {
		switch (sortingBy) {
		case Name:
			return book1.getTitle().compareTo(book2.getTitle());

		case Popularity: { // popularity since last option
			return book1.getLentTimes() > book2.getLentTimes() ? -1
					: book1.getLentTimes() == book2.getLentTimes() ? 0 : 1;

		}
		default:
			return book1.getAuthor().compareTo(book2.getAuthor());
		}
	}

	public void setSortingBy(Order sortingBy) {
		this.sortingBy = sortingBy;
	}
}
