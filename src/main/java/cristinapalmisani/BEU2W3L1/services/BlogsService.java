package cristinapalmisani.BEU2W3L1.services;

import cristinapalmisani.BEU2W3L1.entities.Author;
import cristinapalmisani.BEU2W3L1.entities.Blogpost;
import cristinapalmisani.BEU2W3L1.exceptions.NotFoundException;
import cristinapalmisani.BEU2W3L1.payloads.blogs.NewBlogPostPayload;
import cristinapalmisani.BEU2W3L1.repositories.BlogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogsService {
	@Autowired
	private BlogsRepository blogsRepository;
	@Autowired
	private AuthorsService authorsService;

	public Blogpost save(NewBlogPostPayload body) {
		Author author = authorsService.findById(body.authorId());
		Blogpost newBlogPost = new Blogpost();
		newBlogPost.setReadingTime(body.readingTime());
		newBlogPost.setContent(body.content());
		newBlogPost.setTitle(body.title());
		newBlogPost.setAuthor(author);
		newBlogPost.setCategory(body.category());
		newBlogPost.setCover("http://picsum.photos/200/300");
		return blogsRepository.save(newBlogPost);
	}

	public List<Blogpost> getBlogs() {
		return blogsRepository.findAll();
	}

	public Blogpost findById(int id) {
		return blogsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public void findByIdAndDelete(int id) {
		Blogpost found = this.findById(id);
		blogsRepository.delete(found);
	}

	public Blogpost findByIdAndUpdate(int id, NewBlogPostPayload body) {
		Blogpost found = this.findById(id);

		found.setReadingTime(body.readingTime());
		found.setContent(body.content());
		found.setTitle(body.title());
		found.setCategory(body.category());

		if (found.getAuthor().getId() != body.authorId()) {
			Author newAuthor = authorsService.findById(body.authorId());
			found.setAuthor(newAuthor);
		}

		return blogsRepository.save(found);
	}

	public List<Blogpost> findByAuthor(int authorId) {
		Author author = authorsService.findById(authorId);
		return blogsRepository.findByAuthor(author);
	}
}
