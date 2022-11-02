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
import szathmary.vai.dtos.CommentDto;
import szathmary.vai.entities.Comment;
import szathmary.vai.services.ICommentService;

@Slf4j
@RestController
@RequestMapping("/api/comment")
public class CommentController {

  private final ICommentService commentService;
  private final ModelMapper modelMapper;

  @Autowired
  public CommentController(ICommentService commentService) {
    this.commentService = commentService;
    this.modelMapper = new ModelMapper();
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<CommentDto>> getAllComments() {
    HttpHeaders headers = getHttpHeaders();

    List<Comment> comments = this.commentService.getAllComments();
    List<CommentDto> commentsToReturn = comments.stream()
        .map(x -> this.modelMapper.map(x, CommentDto.class)).collect(Collectors.toList());

    return ResponseEntity.ok().headers(headers).body(commentsToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.GET)
  public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    Comment comment = this.commentService.getCommentById(id);

    if (comment == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    CommentDto commentToReturn = modelMapper.map(comment, CommentDto.class);

    return ResponseEntity.ok().headers(headers).body(commentToReturn);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<CommentDto> createComment(@RequestBody Comment commentToCreate) {
    HttpHeaders headers = getHttpHeaders();

    Comment createdComment = this.commentService.createComment(commentToCreate);

    if (createdComment == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    CommentDto commentDtoToReturn = modelMapper.map(createdComment, CommentDto.class);

    return ResponseEntity.ok().headers(headers).body(commentDtoToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<HttpStatus> deleteCommentById(@PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    Comment foundComment = this.commentService.getCommentById(id);

    if (foundComment == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    this.commentService.deleteComment(foundComment);

    return ResponseEntity.ok().headers(headers).build();
  }

  @RequestMapping(path = "{id}", method = RequestMethod.PUT)
  public ResponseEntity<CommentDto> updateCommentById(@PathVariable Integer id,
      @RequestBody Comment commentToUpdate) {
    HttpHeaders headers = getHttpHeaders();

    Comment foundComment = this.commentService.getCommentById(id);

    if (foundComment == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    BeanUtils.copyProperties(commentToUpdate, foundComment, "commentId");

    this.commentService.updateComment(foundComment);
    CommentDto CommentDtoToReturn = modelMapper.map(foundComment, CommentDto.class);

    return ResponseEntity.ok().headers(headers).body(CommentDtoToReturn);
  }

  private static HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("CommentController", "responded");
    headers.add("Access-Control-Allow-Origin", "*");
    return headers;
  }
}
