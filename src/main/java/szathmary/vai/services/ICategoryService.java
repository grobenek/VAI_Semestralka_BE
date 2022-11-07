package szathmary.vai.services;

import java.util.List;
import szathmary.vai.entities.Category;

public interface ICategoryService {

  List<Category> getAllCategories();

  Category getCategoryById(Integer id);

  void updateCategory(Category categoryToUpdate);

  void deleteCategory(Category categoryToDelete);

  Category createCategory(Category categoryToCreate);
}
