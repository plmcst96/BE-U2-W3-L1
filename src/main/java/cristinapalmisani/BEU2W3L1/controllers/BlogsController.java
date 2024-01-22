package cristinapalmisani.BEU2W3L1.controllers;

import epicode.u5d9hw.entities.Blogpost;
import epicode.u5d9hw.exceptions.BadRequestException;
import epicode.u5d9hw.payloads.blogs.NewBlogPostPayload;
import epicode.u5d9hw.services.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogsController {
	@Autowired
	BlogsService blogsService;

	// 1. - POST http://localhost:3001/blogs (+ req.body)
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED) // <-- 201
	public Blogpost saveBlog(@RequestBody @Validated NewBlogPostPayload body, BindingResult validation) {

		if (validation.hasErrors()) {
			throw new BadRequestException(validation.getAllErrors());
		}
		return blogsService.save(body);
	}

	// 2. - GET http://localhost:3001/blogs
	@GetMapping("")
	public List<Blogpost> getBlogs(@RequestParam(required = false) Integer authorId) {
		if (authorId != null) return blogsService.findByAuthor(authorId);
		else return blogsService.getBlogs();
	}

	// 3. - GET http://localhost:3001/blogs/{id}
	@GetMapping("/{blogId}")
	public Blogpost findById(@PathVariable int blogId) {
		return blogsService.findById(blogId);
	}

	// 4. - PUT http://localhost:3001/blogs/{id} (+ req.body)
	@PutMapping("/{blogId}")
	public Blogpost findAndUpdate(@PathVariable int blogId, @RequestBody NewBlogPostPayload body) {
		return blogsService.findByIdAndUpdate(blogId, body);
	}

	// 5. - DELETE http://localhost:3001/blogs/{id
	@DeleteMapping("/{blogId}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
	public void findAndDelete(@PathVariable int blogId) {
		blogsService.findByIdAndDelete(blogId);
	}
}
