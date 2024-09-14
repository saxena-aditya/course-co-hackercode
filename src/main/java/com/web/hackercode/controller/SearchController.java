package com.web.hackercode.controller;

import com.web.hackercode.dao.ArticleDAO;
import com.web.hackercode.dao.CategoryDAO;
import com.web.hackercode.dao.CourseDAO;
import com.web.hackercode.structures.Article;
import com.web.hackercode.structures.Course;
import com.web.hackercode.structures.EntityCourse;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {

  ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");

  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public ModelAndView search(@RequestParam String query) {
    CourseDAO cdao = ctx.getBean(CourseDAO.class);
    ArticleDAO adao = ctx.getBean(ArticleDAO.class);
    CategoryDAO catdao = ctx.getBean(CategoryDAO.class);

    List<EntityCourse> matchedCourses = cdao.getCoursesByTags(query);
    List<Article> matchedArticles = adao.getArticlesByTags(query);

    System.out.println(matchedArticles.size());
    return new ModelAndView("search/search-view")
      .addObject("articleCount", matchedArticles.size())
      .addObject("courseCount", matchedCourses.size())
      .addObject("articles", matchedArticles)
      .addObject("courses", matchedCourses)
      .addObject("nav", catdao.getNavJSON());
  }
}
