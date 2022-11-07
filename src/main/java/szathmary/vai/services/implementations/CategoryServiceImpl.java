package szathmary.vai.services.implementations;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szathmary.vai.entities.Category;
import szathmary.vai.repositories.CategoryRepository;
import szathmary.vai.services.interfaces.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<Category> getAllCategories() {
    return this.categoryRepository.findAll();
  }

  @Override
  public Category getCategoryById(Integer id) {
    return this.categoryRepository.getReferenceById(id);
  }

  @Override
  public void updateCategory(Category categoryToUpdate) {
    this.categoryRepository.save(categoryToUpdate);
  }

  @Override
  public void deleteCategory(Category categoryToDelete) {
    this.categoryRepository.delete(categoryToDelete);
  }

  @Override
  public Category createCategory(Category categoryToCreate) {
    return this.categoryRepository.save(categoryToCreate);
  }
}
