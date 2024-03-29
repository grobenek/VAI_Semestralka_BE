package szathmary.vai.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import szathmary.vai.dtos.CategoryDto;
import szathmary.vai.entities.Category;
import szathmary.vai.exception.ItemNotFoundException;
import szathmary.vai.services.interfaces.ICategoryNameService;
import szathmary.vai.services.interfaces.ICategoryService;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {

  private final ICategoryService categoryService;

  private final ICategoryNameService categoryNameService;

  private final ModelMapper modelMapper;

  @Autowired
  public CategoryController(ICategoryService categoryService,
      ICategoryNameService categoryNameService) {
    this.categoryService = categoryService;
    this.categoryNameService = categoryNameService;
    this.modelMapper = new ModelMapper();
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    HttpHeaders headers = getHttpHeaders();

    List<Category> categories = this.categoryService.getAllCategories();
    List<CategoryDto> categoryDtosToReturn = categories.stream()
        .map(x -> this.modelMapper.map(x, CategoryDto.class)).collect(Collectors.toList());

    log.info("{} categories returned", categoryDtosToReturn.size());
    return ResponseEntity.ok().headers(headers).body(categoryDtosToReturn);
  }

  @RequestMapping(path = "blog/{blogId}", method = RequestMethod.GET)
  public ResponseEntity<List<CategoryDto>> getCategoriesByBlogId(
      @NotNull(message = "CategoryId cannot be null!")
      @Positive(message = "CategoryId must be positive number!")
      @PathVariable Integer blogId
  ) {
    HttpHeaders httpHeaders = getHttpHeaders();

    log.info("List af all categories with blogId {} requested", blogId);

    List<Category> categories = this.categoryService.getCategoriesByBlogId(blogId);

    if (categories.isEmpty()) {
      throw new ItemNotFoundException("No categories with blogId " + blogId + " were found!");
    }

    List<CategoryDto> categoryDtoListToReturn = categories.stream()
        .map(category -> {
          CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
          categoryDto.setCategoryName(this.categoryNameService.getCategoryNameById(
              category.getCategoryName().getCategoryNameId()).getName());
          return categoryDto;
        }).collect(Collectors.toList());

    log.info("List of {} categories returned", categoryDtoListToReturn.size());

    return ResponseEntity.ok().

        headers(httpHeaders).

        body(categoryDtoListToReturn);

  }

  @RequestMapping(path = "/{id}")
  public ResponseEntity<CategoryDto> getCategoryById(
      @NotNull(message = "CategoryId cannot be null!") @Positive(message = "CategoryId must be positive number!") @PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    Category category = this.categoryService.getCategoryById(id);

    if (category == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    CategoryDto categoryDtoToReturn = modelMapper.map(category, CategoryDto.class);

    log.info("Category with id {} returned", category.getCategoryId());
    return ResponseEntity.ok().headers(headers).body(categoryDtoToReturn);
  }

  @RequestMapping(path = "/categoryName/id/{categoryNameId}/blog/id/{blogId}", method = RequestMethod.POST)
  public ResponseEntity<CategoryDto> createCategory(
      @NotNull(message = "categoryNameId cannot be null!") @Positive(message = "categoryNameId must be positive number!") @PathVariable Integer categoryNameId,
      @NotNull(message = "blogId cannot be null!") @Positive(message = "blogId must be positive number!") @PathVariable Integer blogId) {
    HttpHeaders headers = getHttpHeaders();

    Category createdCategory = this.categoryService.createCategory(new Category(), categoryNameId,
        blogId);

    if (createdCategory == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    CategoryDto categoryDtoToReturn = modelMapper.map(createdCategory, CategoryDto.class);

    log.info("Category with id {} created", categoryDtoToReturn.getCategoryId());
    return ResponseEntity.ok().headers(headers).body(categoryDtoToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<HttpStatus> deleteCategoryById(
      @NotNull(message = "categoryId must not be null!") @Positive(message = "categoryId must be positiveNumber") @PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    Category foundCategory = this.categoryService.getCategoryById(id);

    if (foundCategory == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    this.categoryService.deleteCategory(foundCategory);

    log.info("Category with id {} deleted", foundCategory.getCategoryId());
    return ResponseEntity.ok().headers(headers).build();
  }

  @RequestMapping(path = "{id}/categoryName/id/{categoryNameId}/blog/id/{blogId}", method = RequestMethod.PUT)
  public ResponseEntity<CategoryDto> updateCategoryById(
      @NotNull(message = "categoryNameId cannot be null!") @Positive(message = "categoryNameId must be positive number!") @PathVariable Integer categoryNameId,
      @NotNull(message = "blogId cannot be null!") @Positive(message = "blogId must be positive number!") @PathVariable Integer blogId,
      @NotNull(message = "categoryId cannot be null!") @Positive(message = "categoryId must be positive number!") @PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    Category foundCategory = this.categoryService.getCategoryById(id);

    if (foundCategory == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    this.categoryService.updateCategory(foundCategory, categoryNameId, blogId);
    CategoryDto categoryNameDtoToReturn = modelMapper.map(foundCategory, CategoryDto.class);

    log.info("Category with id {} updated", foundCategory.getCategoryId());
    return ResponseEntity.ok().headers(headers).body(categoryNameDtoToReturn);
  }

  private static HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("CategoryController", "responded");
    headers.add("Access-Control-Allow-Origin", "*");
    return headers;
  }
}
