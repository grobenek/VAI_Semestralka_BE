package szathmary.vai.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import szathmary.vai.dtos.CategoryNameDto;
import szathmary.vai.entities.CategoryName;
import szathmary.vai.services.implementations.CategoryNameServiceImpl;

@Slf4j
@Valid
@RestController
@RequestMapping(path = "/api/categoryName")
public class CategoryNameController {

  private final CategoryNameServiceImpl categoryNameService;

  private final ModelMapper modelMapper;

  @Autowired
  public CategoryNameController(CategoryNameServiceImpl categoryNameService) {
    this.categoryNameService = categoryNameService;
    this.modelMapper = new ModelMapper();
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<CategoryNameDto>> getAllCategoryNames() {
    HttpHeaders headers = getHttpHeaders();

    List<CategoryName> categoryNames = this.categoryNameService.getAllCategoryNames();
    List<CategoryNameDto> categoryNameDtosToReturn = categoryNames.stream()
        .map(x -> this.modelMapper.map(x, CategoryNameDto.class))
        .collect(Collectors.toList());

    return ResponseEntity.ok().headers(headers).body(categoryNameDtosToReturn);
  }

  @RequestMapping(path = "/{id}")
  public ResponseEntity<CategoryNameDto> getCategoryNameById(
      @NotNull(message = "CategoryNameId cannot be null!")
      @Positive(message = "CategoryNameId must be positive number!")
      @PathVariable Integer id
  ) {
    HttpHeaders headers = getHttpHeaders();

    CategoryName categoryName = this.categoryNameService.getCategoryNameById(id);

    if (categoryName == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    CategoryNameDto categoryNameDtoToReturn = modelMapper.map(categoryName, CategoryNameDto.class);

    return ResponseEntity.ok().headers(headers).body(categoryNameDtoToReturn);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<CategoryNameDto> createCategoryName(
      @Valid @RequestBody CategoryName categoryNameToCreate) {
    HttpHeaders headers = getHttpHeaders();

    CategoryName createdCategoryName = this.categoryNameService.createCategoryName(
        categoryNameToCreate);

    if (createdCategoryName == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    CategoryNameDto categoryNameDtoToReturn = modelMapper.map(createdCategoryName,
        CategoryNameDto.class);

    return ResponseEntity.ok().headers(headers).body(categoryNameDtoToReturn);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<HttpStatus> deleteCategoryNameById(
      @NotNull(message = "categoryNameId must not be null!")
      @Positive(message = "categoryNameId must be positiveNumber")
      @PathVariable Integer id) {
    HttpHeaders headers = getHttpHeaders();

    CategoryName foundCategoryName = this.categoryNameService.getCategoryNameById(id);

    if (foundCategoryName == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    this.categoryNameService.deleteCategoryName(foundCategoryName);

    return ResponseEntity.ok().headers(headers).build();
  }

  @RequestMapping(path = "{id}", method = RequestMethod.PUT)
  public ResponseEntity<CategoryNameDto> updateCategoryNameById(
      @NotNull(message = "categoryNameId cannot be null!")
      @Positive(message = "categoryNameId must be positive number!")
      @PathVariable Integer id,
      @Valid @RequestBody CategoryName categoryNameToUpdate
  ) {
    HttpHeaders headers = getHttpHeaders();

    CategoryName foundCategoryName = this.categoryNameService.getCategoryNameById(id);

    if (foundCategoryName == null) {
      return ResponseEntity.notFound().headers(headers).build();
    }

    BeanUtils.copyProperties(categoryNameToUpdate, foundCategoryName, "categoryNameId");

    this.categoryNameService.updateCategoryName(foundCategoryName);
    CategoryNameDto categoryNameDtoToReturn = modelMapper.map(foundCategoryName,
        CategoryNameDto.class);

    return ResponseEntity.ok().headers(headers).body(categoryNameDtoToReturn);
  }

  private static HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("CategoryNameController", "responded");
    headers.add("Access-Control-Allow-Origin", "*");
    return headers;
  }
}
