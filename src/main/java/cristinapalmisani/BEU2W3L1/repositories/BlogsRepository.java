package cristinapalmisani.BEU2W3L1.repositories;

import cristinapalmisani.BEU2W3L1.entities.Author;
import cristinapalmisani.BEU2W3L1.entities.Blogpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogsRepository extends JpaRepository<Blogpost, Integer> {
	List<Blogpost> findByAuthor(Author author);
}
