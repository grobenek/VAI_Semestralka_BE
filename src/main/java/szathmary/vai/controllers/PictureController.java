package szathmary.vai.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import szathmary.vai.dtos.PictureDto;
import szathmary.vai.entities.Picture;
import szathmary.vai.exception.ItemNotFoundException;
import szathmary.vai.repositories.PictureRepository;
import szathmary.vai.services.interfaces.IPictureService;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/picture")
public class PictureController {

  private final IPictureService pictureService;

  private final ModelMapper modelMapper;

  @Autowired
  public PictureController(IPictureService pictureService,
      PictureRepository pictureRepository) {
    this.pictureService = pictureService;
    this.modelMapper = new ModelMapper();
  }

  @RequestMapping(path = "{id}", method = RequestMethod.GET)
  public ResponseEntity<PictureDto> getPictureById(
      @NotNull(message = "pictureId must not be null!")
      @Positive(message = "pictureId must be positive number")
      @PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    log.info("Getting picture with id {}", id);
    Picture picture = this.pictureService.getPictureById(id);

    if (picture == null) {
      throw new ItemNotFoundException("Picture with id " + id + " not found!");
    }

    PictureDto pictureDtoToReturn = this.modelMapper.map(picture, PictureDto.class);

    String pictureInString = new String(picture.getData());
    pictureDtoToReturn.setData(pictureInString);

    log.info("Picture with id {} and filename {} returned", pictureDtoToReturn.getPictureId(),
        pictureDtoToReturn.getFileName());
    return ResponseEntity.ok().headers(headers).body(pictureDtoToReturn);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<PictureDto> createPicture(
      @Valid @RequestBody Picture picture
  ) {

    HttpHeaders httpHeaders = getHttpHeaders();

    Picture existingPicture = this.pictureService.findPictureByFileName(picture.getFileName());
    if (existingPicture != null) {
      PictureDto pictureDtoToReturn = this.modelMapper.map(existingPicture, PictureDto.class);

      log.info("Picture with fileName {} already exists, returning it now",
          pictureDtoToReturn.getFileName());

      return ResponseEntity.ok().headers(httpHeaders).body(pictureDtoToReturn);
    }

    Picture createdPicture = this.pictureService.createPicture(picture);

    if (createdPicture == null) {
      throw new ItemNotFoundException("Picture with id " + picture.getPictureId() + " not found!");
    }

    log.info("Picture with id {} and file name {} created!", createdPicture.getPictureId(),
        createdPicture.getFileName());

    PictureDto pictureDtoToReturn = this.modelMapper.map(createdPicture, PictureDto.class);
    return ResponseEntity.ok().headers(httpHeaders).body(pictureDtoToReturn);
  }


  private static HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("BlogController", "responded");
    headers.add("Access-Control-Allow-Origin", "*");
    return headers;
  }
}








