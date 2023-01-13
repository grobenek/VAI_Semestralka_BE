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

    log.info("GetAllBlogs requested");

    List<Blog> blogs = this.blogService.getAllBlogs();
    List<BlogDto> blogsToReturn = blogs.stream().map(x -> this.modelMapper.map(x, BlogDto.class))
        .collect(Collectors.toList());

    log.info("List of {} blogs returned", blogsToReturn.size());

    return ResponseEntity.ok().headers(headers).body(blogsToReturn);
  }

  @RequestMapping(path = "user/{userId}", method = RequestMethod.GET)
  public ResponseEntity<List<BlogDto>> getBlogsByAuthorUserId(
      @NotNull(message = "userId must not be null!")
      @Positive(message = "userId must be positiveNumber")
      @PathVariable Integer userId
  ) {
    HttpHeaders httpHeaders = getHttpHeaders();

    log.info("List of blogs with userId {} requested", userId);

    List<Blog> blogs = this.blogService.getBlogsByAuthorUserId(userId);

    if (blogs.isEmpty()) {
      throw new ItemNotFoundException("No blogs with userId " + userId + " were found!");
    }

    List<BlogDto> blogDtoListToReturn = blogs.stream()
        .map(blog -> this.modelMapper.map(blog, BlogDto.class)).collect(
            Collectors.toList());

    log.info("List of {} blogs returned", blogDtoListToReturn.size());

    return ResponseEntity.ok().headers(httpHeaders).body(blogDtoListToReturn);
  }

  @RequestMapping(path = "category/{categoryId}", method = RequestMethod.GET)
  public ResponseEntity<List<BlogDto>> getBlogsByCategoriesCategoryId(
      @NotNull(message = "categoryId must not be null!")
      @Positive(message = "categoryId must be positiveNumber")
      @PathVariable Integer categoryId
  ) {
    HttpHeaders httpHeaders = getHttpHeaders();

    log.info("List of blogs with categoryId {} requested", categoryId);

    List<Blog> blogs = this.blogService.getBlogsByCategoriesCategoryId(categoryId);

    if (blogs.isEmpty()) {
      throw new ItemNotFoundException("No blogs with categoryId " + categoryId + " were found!");
    }

    List<BlogDto> blogDtoListToReturn = blogs.stream()
        .map(blog -> this.modelMapper.map(blog, BlogDto.class)).collect(
            Collectors.toList());

    log.info("List of {} blogs returned", blogDtoListToReturn.size());

    return ResponseEntity.ok().headers(httpHeaders).body(blogDtoListToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.GET)
  public ResponseEntity<BlogDto> getBlogById(
      @NotNull(message = "blogId must not be null!")
      @Positive(message = "blogID must be positiveNumber")
      @PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    log.info("Getting blog with id {}", id);
    Blog blog = this.blogService.getBlogById(id);

    if (blog == null) {
      throw new ItemNotFoundException("Blog with id " + id + " not found!");
    }

    BlogDto blogDtoToReturn = modelMapper.map(blog, BlogDto.class);

    log.info("blog with id {} returned", blogDtoToReturn.getBlogId());
    return ResponseEntity.ok().headers(headers).body(blogDtoToReturn);
  }

  @RequestMapping(path = "/author/id/{authorId}/{pictureId}", method = RequestMethod.POST)
  public ResponseEntity<BlogDto> createBlog(
      @Valid @RequestBody Blog blogToCreate,
      @NotNull(message = "authorId must not be null!")
      @Positive(message = "authorId must be positiveNumber")
      @PathVariable Integer authorId,
      @NotNull(message = "authorId must not be null!")
      @Positive(message = "authorId must be positiveNumber") @PathVariable Integer pictureId) {
    HttpHeaders headers = getHttpHeaders();

    Blog createdBlog = this.blogService.createBlog(blogToCreate, authorId, pictureId);

    if (createdBlog == null) {
      throw new ItemNotFoundException("Blog with id " + blogToCreate.getBlogId() + " not found!");
    }

    log.info("Blog with id {} created!", createdBlog.getBlogId());

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

    log.info("Blog with id {} deleted", id);
    return ResponseEntity.ok().headers(headers).build();
  }

  @RequestMapping(path = "{idBlog}/author/id/{idAuthor}/picture/id/{idPicture}", method = RequestMethod.PUT)
  public ResponseEntity<BlogDto> updateBlogById(
      @NotNull(message = "blogId must not be null!")
      @Positive(message = "blogId must be positiveNumber")
      @PathVariable Integer idBlog,
      @Valid @RequestBody Blog blogToUpdate,
      @NotNull(message = "authorId must not be null!")
      @Positive(message = "authorId must be positiveNumber")
      @PathVariable Integer idAuthor,
      @NotNull(message = "authorId must not be null!")
      @Positive(message = "authorId must be positiveNumber")
      @PathVariable Integer idPicture) {
    HttpHeaders headers = getHttpHeaders();

    Blog foundBlog = this.blogService.getBlogById(idBlog);

    if (foundBlog == null) {
      throw new ItemNotFoundException("Blog with id " + blogToUpdate.getBlogId() + " not found!");
    }

    BeanUtils.copyProperties(blogToUpdate, foundBlog, "blogId", "author", "picture");

    this.blogService.updateBlog(foundBlog, idAuthor, idPicture);
    BlogDto blogDtoToReturn = modelMapper.map(foundBlog, BlogDto.class);

    log.info("Blog with id {} updated", blogToUpdate.getBlogId());
    return ResponseEntity.ok().headers(headers).body(blogDtoToReturn);
  }

  private static HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("BlogController", "responded");
    headers.add("Access-Control-Allow-Origin", "*");
    return headers;
  }
}
