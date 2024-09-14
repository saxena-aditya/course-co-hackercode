package com.web.hackercode.controller;

import com.web.hackercode.dao.CategoryDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoryController {

  ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");

  @RequestMapping(
    value = "/api/sub-categories/delete/{categoryID}/{subCategoryID}"
  )
  @ResponseBody
  public boolean deleteSubCat(
    @PathVariable int categoryID,
    @PathVariable int subCategoryID
  ) {
    CategoryDAO catdao = ctx.getBean(CategoryDAO.class);
    return catdao.deleteSubCategory(categoryID, subCategoryID);
  }

  @RequestMapping(value = "/api/sub-categories/add/{categoryID}")
  @ResponseBody
  public int addSubCat(
    @PathVariable int categoryID,
    @RequestParam String subCategory
  ) {
    CategoryDAO catdao = ctx.getBean(CategoryDAO.class);
    return catdao.addSubCategory(categoryID, subCategory);
  }

  @RequestMapping(value = "/api/categories/delete/{categoryID}")
  @ResponseBody
  public boolean deleteCat(@PathVariable int categoryID) {
    CategoryDAO catdao = ctx.getBean(CategoryDAO.class);
    return catdao.deleteCategory(categoryID);
  }

  @RequestMapping(value = "/api/categories/add")
  @ResponseBody
  public int addCat(@RequestParam String category) {
    CategoryDAO catdao = ctx.getBean(CategoryDAO.class);
    return catdao.addCategory(category);
  }
}
