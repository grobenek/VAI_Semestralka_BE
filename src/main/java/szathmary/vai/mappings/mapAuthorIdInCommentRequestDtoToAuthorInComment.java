package szathmary.vai.mappings;

import org.modelmapper.PropertyMap;
import szathmary.vai.dtos.CommentRequestDto;
import szathmary.vai.entities.Comment;

public class mapAuthorIdInCommentRequestDtoToAuthorInComment extends
    PropertyMap<CommentRequestDto, Comment> {

  protected void configure() {
    map().getAuthor().setUserId(source.getAuthorId());
  }

}
