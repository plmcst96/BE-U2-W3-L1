package cristinapalmisani.BEU2W3L1.repositories;

import cristinapalmisani.BEU2W3L1.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorsRepository extends JpaRepository<Author, Integer> {
	Optional<Author> findByEmail(String email);
}
