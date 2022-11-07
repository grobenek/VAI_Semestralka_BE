package szathmary.vai.services.implementations;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szathmary.vai.entities.Blog;
import szathmary.vai.entities.Category;
import szathmary.vai.entities.CategoryName;
import szathmary.vai.repositories.BlogRepository;
import szathmary.vai.repositories.CategoryNameRepository;
import szathmary.vai.repositories.CategoryRepository;
import szathmary.vai.services.interfaces.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

  private final CategoryRepository categoryRepository;

  private final BlogRepository blogRepository;

  private final CategoryNameRepository categoryNameRepository;

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRepository, BlogRepository blogRepository,
      CategoryNameRepository categoryNameRepository) {
    this.categoryRepository = categoryRepository;
    this.blogRepository = blogRepository;
    this.categoryNameRepository = categoryNameRepository;
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
  public void updateCategory(Category categoryToUpdate, Integer categoryNameId, Integer blogId) {
    Blog blog = blogRepository.getReferenceById(blogId);
    CategoryName categoryName = categoryNameRepository.getReferenceById(categoryNameId);

    categoryToUpdate.setBlog(blog);
    categoryToUpdate.setCategoryName(categoryName);

    this.categoryRepository.save(categoryToUpdate);
  }

  @Override
  public void deleteCategory(Category categoryToDelete) {
    this.categoryRepository.delete(categoryToDelete);
  }

  @Override
  public Category createCategory(Category categoryToCreate, Integer categoryNameId,
      Integer blogId) {
    Blog blog = blogRepository.getReferenceById(blogId);
    CategoryName categoryName = categoryNameRepository.getReferenceById(categoryNameId);

    categoryToCreate.setBlog(blog);
    categoryToCreate.setCategoryName(categoryName);

    return this.categoryRepository.save(categoryToCreate);
  }
}
