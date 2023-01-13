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
import szathmary.vai.dtos.CommentRequestDto;
import szathmary.vai.dtos.CommentResponseDto;
import szathmary.vai.entities.Comment;
import szathmary.vai.exception.ItemNotFoundException;
import szathmary.vai.mappings.mapAuthorIdInCommentRequestDtoToAuthorInComment;
import szathmary.vai.services.interfaces.ICommentService;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/comment")
public class CommentController {

  private final ICommentService commentService;
  private final ModelMapper modelMapper;

  @Autowired
  public CommentController(ICommentService commentService) {
    this.commentService = commentService;
    this.modelMapper = new ModelMapper();
    this.modelMapper.addMappings(new mapAuthorIdInCommentRequestDtoToAuthorInComment());
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<CommentResponseDto>> getAllComments() {
    HttpHeaders headers = getHttpHeaders();

    List<Comment> comments = this.commentService.getAllComments();
    List<CommentResponseDto> commentsToReturn = comments.stream()
        .map(x -> this.modelMapper.map(x, CommentResponseDto.class)).collect(Collectors.toList());

    log.info("All comments returned");
    return ResponseEntity.ok().headers(headers).body(commentsToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.GET)
  public ResponseEntity<CommentResponseDto> getCommentById(
      @NotNull(message = "commentId must not be null!")
      @Positive(message = "commentId must be positive number!")
      @PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    Comment comment = this.commentService.getCommentById(id);

    if (comment == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    CommentResponseDto commentToReturn = modelMapper.map(comment, CommentResponseDto.class);

    log.info("Comment with id {} returned", commentToReturn.getCommentId());
    return ResponseEntity.ok().headers(headers).body(commentToReturn);
  }

  @RequestMapping(path = "blog/{blogId}", method = RequestMethod.GET)
  public ResponseEntity<List<CommentResponseDto>> getAllCommentsByBlogId(
      @NotNull(message = "blogtId must not be null!")
      @Positive(message = "blogtId must be positive number!")
      @PathVariable Integer blogId
  ) {
    HttpHeaders httpHeaders = getHttpHeaders();

    log.info("All comments with blogId {} requested", blogId);

    List<Comment> comments = this.commentService.getAllCommentsByBlogId(blogId);

    if (comments.isEmpty()) {
      throw new ItemNotFoundException("No comments were found for blogId " + blogId);
    }

    List<CommentResponseDto> commentResponseDtoListToReturn = comments.stream()
        .map(comment -> this.modelMapper.map(comment, CommentResponseDto.class)).collect(
            Collectors.toList());

    log.info("{} comments with blogId {} returned", commentResponseDtoListToReturn.size(), blogId);

    return ResponseEntity.ok().headers(httpHeaders).body(commentResponseDtoListToReturn);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<CommentResponseDto> createComment(
      @Valid @RequestBody CommentRequestDto commentRequestDtoToCreate) {
    log.info("createComment in CommentController started");

    HttpHeaders headers = getHttpHeaders();

    Comment commentToCreate = modelMapper.map(commentRequestDtoToCreate,
        Comment.class);

    Comment createdComment = this.commentService.createComment(commentToCreate);
    log.info("createComment in CommentController commentToCreate id {}",
        createdComment.getCommentId());

    CommentResponseDto commentRequestDtoToReturn = modelMapper.map(createdComment,
        CommentResponseDto.class);
    log.info("comment created with id {}", createdComment.getCommentId());
    return ResponseEntity.ok().headers(headers).body(commentRequestDtoToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<HttpStatus> deleteCommentById(
      @NotNull(message = "commentId must not be null!")
      @Positive(message = "commentId must be positive number!")
      @PathVariable Integer id) {
    log.info("deleteCommentById started in CommentController");
    HttpHeaders headers = getHttpHeaders();

    Comment foundComment = this.commentService.getCommentById(id);

    if (foundComment == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    this.commentService.deleteComment(foundComment);

    log.info("comment with id {} deleted", foundComment.getCommentId());

    return ResponseEntity.ok().headers(headers).build();
  }

  @RequestMapping(method = RequestMethod.PUT)
  public ResponseEntity<CommentResponseDto> updateComment(
      @Valid @RequestBody CommentRequestDto commentToUpdate) {
    log.info("updateComment started in CommentController");

    HttpHeaders headers = getHttpHeaders();

    Comment commentToUpdateToSearch = modelMapper.map(commentToUpdate, Comment.class);

    log.info("Comment from CommentRequestDTO is  id: {}, authorId: {}, blogId: {}, text: {}",
        commentToUpdateToSearch.getCommentId(), commentToUpdateToSearch.getAuthor().getUserId(),
        commentToUpdateToSearch.getBlog().getBlogId(), commentToUpdateToSearch.getText());

    Comment foundComment = this.commentService.getCommentById(
        commentToUpdateToSearch.getCommentId());

    log.info("Founded comment to update with id {}", foundComment.getCommentId());

    BeanUtils.copyProperties(commentToUpdateToSearch, foundComment, "commentId", "timestamp");

    //set isEdited to true
    foundComment.setIsEdited(true);

    log.info(
        "Comment that will be updated is  id: {}, authorId: {}, blogId: {}, text: {}, isEdited: {}, timestamp: {}",
        foundComment.getCommentId(), foundComment.getAuthor().getUserId(),
        foundComment.getBlog().getBlogId(), foundComment.getText(), foundComment.getIsEdited(),
        foundComment.getTimestamp());

    this.commentService.updateComment(foundComment);
    CommentResponseDto commentResponseDto = modelMapper.map(foundComment,
        CommentResponseDto.class);

    log.info("updateComment ended in CommentController");
    return ResponseEntity.ok().headers(headers).body(commentResponseDto);
  }

  private static HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("CommentController", "responded");
    headers.add("Access-Control-Allow-Origin", "*");
    return headers;
  }
}
