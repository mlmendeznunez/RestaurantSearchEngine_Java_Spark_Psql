import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
  staticFileLocation("/public");
  String layout = "templates/layout.vtl";

  get("/", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    //categories here can be anything as long as it matches $categories
    model.put("cuisines", Cuisine.all());
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/cuisines", (request, response) -> { //post route must match form action
    HashMap<String,Object> model = new HashMap<String, Object>();
    String foodtype = request.queryParams("foodtype");
    Cuisine newCuisine = new Cuisine(foodtype);
    newCuisine.save();
    model.put("cuisines", Cuisine.all());
    //put arraylist of cuisines on page
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/cuisines", (request, response) -> {
  //need to put :id in the url so that we can grab it below
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("cuisines", Cuisine.all());
    model.put("template", "templates/admin.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());


}//end of main

}//end of app
