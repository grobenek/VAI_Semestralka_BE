package szathmary.vai.services.interfaces;

import szathmary.vai.entities.Picture;

public interface IPictureService {

  Picture getPictureById(Integer id);

  Picture updatePicture(Picture pictureToUpdate);

  void deletePicture(Picture pictureToDelete);

  Picture createPicture(Picture pictureToCreate);

  Picture findPictureByFileName(String fileName);
}
