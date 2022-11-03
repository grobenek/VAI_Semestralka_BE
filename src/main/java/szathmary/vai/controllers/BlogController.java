package szathmary.vai.controllers;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import szathmary.vai.dtos.BlogDto;
import szathmary.vai.entities.Blog;
import szathmary.vai.services.IBlogService;

@Slf4j
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
  public ResponseEntity<BlogDto> getBlogById(@PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    Blog blog = this.blogService.getBlogById(id);

    if (blog == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    BlogDto blogDtoToReturn = modelMapper.map(blog, BlogDto.class);

    return ResponseEntity.ok().headers(headers).body(blogDtoToReturn);
  }

  @RequestMapping(path = "/author/id/{authorId}", method = RequestMethod.POST)
  public ResponseEntity<BlogDto> createBlog(@RequestBody Blog blogToCreate,
      @PathVariable Integer authorId) {
    HttpHeaders headers = getHttpHeaders();

    Blog createdBlog = this.blogService.createBlog(blogToCreate, authorId);

    if (createdBlog == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    BlogDto blogDtoToReturn = modelMapper.map(createdBlog, BlogDto.class);

    return ResponseEntity.ok().headers(headers).body(blogDtoToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<HttpStatus> deleteBlogById(@PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    Blog foundBlog = this.blogService.getBlogById(id);

    if (foundBlog == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    this.blogService.deleteBlog(foundBlog);

    return ResponseEntity.ok().headers(headers).build();
  }

  @RequestMapping(path = "{id}/author/id/{idAuthor}", method = RequestMethod.PUT)
  public ResponseEntity<BlogDto> updateBlogById(@PathVariable Integer id,
      @RequestBody Blog blogToUpdate, @PathVariable Integer idAuthor) {
    HttpHeaders headers = getHttpHeaders();

    Blog foundBlog = this.blogService.getBlogById(id);

    if (foundBlog == null) {
      return ResponseEntity.notFound().headers(headers).build();
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
