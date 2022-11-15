package szathmary.vai.services.implementations;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szathmary.vai.entities.CategoryName;
import szathmary.vai.repositories.CategoryNameRepository;
import szathmary.vai.services.interfaces.ICategoryNameService;

@Service
public class CategoryNameServiceImpl implements ICategoryNameService {

  CategoryNameRepository categoryNameRepository;

  @Autowired
  public CategoryNameServiceImpl(CategoryNameRepository categoryNameRepository) {
    this.categoryNameRepository = categoryNameRepository;
  }

  @Override
  public List<CategoryName> getAllCategoryNames() {
    return this.categoryNameRepository.findAll();
  }

  @Override
  public CategoryName getCategoryNameById(Integer id) {
    return this.categoryNameRepository.getReferenceById(id);
  }

  @Override
  public void updateCategoryName(CategoryName categoryNameToUpdate) {
    this.categoryNameRepository.save(categoryNameToUpdate);
  }

  @Override
  public void deleteCategoryName(CategoryName categoryNameToDelete) {
    this.categoryNameRepository.deleteById(categoryNameToDelete.getCategoryNameId());
  }

  @Override
  public CategoryName createCategoryName(CategoryName categoryNameToCreate) {
    return this.categoryNameRepository.save(categoryNameToCreate);
  }
}
