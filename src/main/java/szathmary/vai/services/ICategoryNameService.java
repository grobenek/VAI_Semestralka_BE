package szathmary.vai.services;

import java.util.List;
import szathmary.vai.entities.CategoryName;

public interface ICategoryNameService {

  List<CategoryName> getAllCategoryNames();

  CategoryName getCategoryNameById(Integer id);

  void updateCategoryName(CategoryName categoryNameToUpdate);

  void deleteCategoryName(CategoryName categoryNameToDelete);

  CategoryName createCategoryName(CategoryName categoryNameToCreate);
}
