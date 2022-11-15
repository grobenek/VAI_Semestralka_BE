package szathmary.vai.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import szathmary.vai.dtos.BlogDto;
import szathmary.vai.entities.Blog;
import szathmary.vai.exception.ItemNotFoundException;
import szathmary.vai.services.interfaces.IBlogService;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/blog")
public class BlogController {

  private final IBlogService blogService;
  private final ModelMapper modelMapper;

  @Autowired
  public BlogController(IBlogService blogService) {
    this.blogService = blogService;
    this.modelMapper = new ModelMapper();
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<BlogDto>> getAllBlogs() {
    HttpHeaders headers = getHttpHeaders();

    List<Blog> blogs = this.blogService.getAllBlogs();
    List<BlogDto> blogsToReturn = blogs.stream().map(x -> this.modelMapper.map(x, BlogDto.class))
        .collect(Collectors.toList());

    return ResponseEntity.ok().headers(headers).body(blogsToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.GET)
  public ResponseEntity<BlogDto> getBlogById(
      @NotNull(message = "blogId must not be null!")
      @Positive(message = "blogID must be positiveNumber")
      @PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    Blog blog = this.blogService.getBlogById(id);

    if (blog == null) {
      throw new ItemNotFoundException("Blog with id " + id + " not found!");
    }

    BlogDto blogDtoToReturn = modelMapper.map(blog, BlogDto.class);

    return ResponseEntity.ok().headers(headers).body(blogDtoToReturn);
  }

  @RequestMapping(path = "/author/id/{authorId}", method = RequestMethod.POST)
  public ResponseEntity<BlogDto> createBlog(
      @Valid @RequestBody Blog blogToCreate,
      @NotNull(message = "authorId must not be null!")
      @Positive(message = "authorId must be positiveNumber")
      @PathVariable Integer authorId) {
    HttpHeaders headers = getHttpHeaders();

    Blog createdBlog = this.blogService.createBlog(blogToCreate, authorId);

    if (createdBlog == null) {
      throw new ItemNotFoundException("Blog with id " + blogToCreate.getBlogId() + " not found!");
    }

    BlogDto blogDtoToReturn = modelMapper.map(createdBlog, BlogDto.class);

    return ResponseEntity.ok().headers(headers).body(blogDtoToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<HttpStatus> deleteBlogById(
      @NotNull(message = "blogId must not be null!")
      @Positive(message = "blogId must be positiveNumber")
      @PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    Blog foundBlog = this.blogService.getBlogById(id);

    if (foundBlog == null) {
      throw new ItemNotFoundException("Blog with id " + id + " not found!");
    }

    this.blogService.deleteBlog(foundBlog);

    return ResponseEntity.ok().headers(headers).build();
  }

  @RequestMapping(path = "{idBlog}/author/id/{idAuthor}", method = RequestMethod.PUT)
  public ResponseEntity<BlogDto> updateBlogById(
      @NotNull(message = "blogId must not be null!")
      @Positive(message = "blogId must be positiveNumber")
      @PathVariable Integer idBlog,
      @Valid @RequestBody Blog blogToUpdate,
      @NotNull(message = "authorId must not be null!")
      @Positive(message = "authorId must be positiveNumber")
      @PathVariable Integer idAuthor) {
    HttpHeaders headers = getHttpHeaders();

    Blog foundBlog = this.blogService.getBlogById(idBlog);

    if (foundBlog == null) {
      throw new ItemNotFoundException("Blog with id " + blogToUpdate.getBlogId() + " not found!");
    }

    BeanUtils.copyProperties(blogToUpdate, foundBlog, "blogId", "author");

    this.blogService.updateBlog(foundBlog, idAuthor);
    BlogDto blogDtoToReturn = modelMapper.map(foundBlog, BlogDto.class);

    return ResponseEntity.ok().headers(headers).body(blogDtoToReturn);
  }

  private static HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("BlogController", "responded");
    headers.add("Access-Control-Allow-Origin", "*");
    return headers;
  }
}
