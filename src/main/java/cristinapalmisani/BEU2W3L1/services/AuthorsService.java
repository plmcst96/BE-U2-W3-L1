package cristinapalmisani.BEU2W3L1.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import cristinapalmisani.BEU2W3L1.config.MailgunSender;
import cristinapalmisani.BEU2W3L1.entities.Author;
import cristinapalmisani.BEU2W3L1.exceptions.BadRequestException;
import cristinapalmisani.BEU2W3L1.exceptions.NotFoundException;
import cristinapalmisani.BEU2W3L1.payloads.authors.NewAuthorDTO;
import cristinapalmisani.BEU2W3L1.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AuthorsService {
	@Autowired
	private Cloudinary cloudinaryUploader;

	@Autowired
	private MailgunSender emailSender;
	@Autowired
	private AuthorsRepository authorsRepository;


	public Author save(NewAuthorDTO body) throws IOException {
		authorsRepository.findByEmail(body.email()).ifPresent(user -> {
			throw new BadRequestException("L'email " + body.email() + " è già stata utilizzata");
		});
		Author newAuthor = new Author();
		newAuthor.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
		newAuthor.setName(body.name());
		newAuthor.setEmail(body.email());
		newAuthor.setSurname(body.surname());
		newAuthor.setDateOfBirth(body.dateOfBirth());
		emailSender.sendRegistrationEmail(newAuthor);

		return authorsRepository.save(newAuthor);
	}

	public Page<Author> getAuthors(int page, int size, String sort) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return authorsRepository.findAll(pageable);
	}

	public Author findById(int id) {
		return authorsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public void findByIdAndDelete(int id) {
		Author found = this.findById(id);
		authorsRepository.delete(found);
	}

	public Author findByIdAndUpdate(int id, Author body) {

		Author found = this.findById(id);
		found.setEmail(body.getEmail());
		found.setName(body.getName());
		found.setSurname(body.getSurname());
		found.setDateOfBirth(body.getDateOfBirth());
		found.setAvatar(body.getAvatar());
		return authorsRepository.save(found);
	}

	public Author uploadAvatar(int id, MultipartFile file) throws IOException {
		Author found = this.findById(id);
		String avatarURL = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
		found.setAvatar(avatarURL);
		return authorsRepository.save(found);
	}
}
