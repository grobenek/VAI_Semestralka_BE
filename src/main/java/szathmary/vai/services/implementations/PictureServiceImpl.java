package szathmary.vai.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szathmary.vai.entities.Picture;
import szathmary.vai.exception.ImageIsNotValidException;
import szathmary.vai.repositories.PictureRepository;
import szathmary.vai.services.interfaces.IPictureService;
import szathmary.vai.validators.Base64ImageValidator;

@Service
public class PictureServiceImpl implements IPictureService {

  private final PictureRepository pictureRepository;

  @Autowired
  public PictureServiceImpl(PictureRepository pictureRepository) {
    this.pictureRepository = pictureRepository;
  }

  @Override
  public Picture getPictureById(Integer id) {
    return this.pictureRepository.getReferenceById(id);
  }

  @Override
  public Picture updatePicture(Picture pictureToUpdate) {
    Picture existingPicture = this.getPictureById(pictureToUpdate.getPictureId());

    BeanUtils.copyProperties(existingPicture, pictureToUpdate, "pictureId");

    return this.pictureRepository.save(pictureToUpdate);
  }

  @Override
  public void deletePicture(Picture pictureToDelete) {
    this.pictureRepository.delete(pictureToDelete);
  }

  @Override
  public Picture createPicture(Picture pictureToCreate) {

    if (Base64ImageValidator.isValidImage(pictureToCreate.getData())) {
      return this.pictureRepository.save(pictureToCreate);
    }

    throw new ImageIsNotValidException(
        "Image with file name " + pictureToCreate.getFileName() + " is not valid base64 image!");
  }
}
