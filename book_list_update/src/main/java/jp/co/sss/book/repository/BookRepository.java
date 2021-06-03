package jp.co.sss.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.book.entity.Book;
import jp.co.sss.book.entity.Genre;

public interface BookRepository extends JpaRepository<Book, Integer> {
	List<Book> findAllByOrderByBookIdAsc();
	List<Book> findByBookNameLike(String bookName);
	List<Book> findByGenre(Genre genre);
//	Book findByGenre(Genre genre);

}
