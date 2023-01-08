package szathmary.vai.validators;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import javax.imageio.ImageIO;

/**
 * FROM INTERNET!
 */
public class Base64ImageValidator {

  public static boolean isValidImage(String base64String) {
    try {
      Decoder decoder = Base64.getDecoder();
      byte[] imageBytes = decoder.decode(base64String);

      BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
      return image != null;
    } catch (IOException e) {
      return false;
    }
  }
}
