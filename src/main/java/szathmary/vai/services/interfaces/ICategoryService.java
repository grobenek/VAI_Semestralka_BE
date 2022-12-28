package szathmary.vai.services.interfaces;

import java.util.List;
import szathmary.vai.entities.Category;

public interface ICategoryService {

  List<Category> getAllCategories();

  Category getCategoryById(Integer id);

  List<Category> getCategoriesByBlogId(Integer blogId);

  void updateCategory(Category categoryToUpdate, Integer categoryNameId, Integer blogId);

  void deleteCategory(Category categoryToDelete);

  Category createCategory(Category categoryToCreate, Integer categoryNameId, Integer blogId);
}
