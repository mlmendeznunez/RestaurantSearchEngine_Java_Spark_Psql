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

    model.put("restaurants", Restaurant.all());
    model.put("cuisines", Cuisine.all());
    model.put("template", "templates/admin.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/restaurants", (request, response) -> {
    ///restaurants in index.vtl
    //posts information from restaurant form on homepage.
    HashMap<String, Object> model = new HashMap<String, Object>();
    Cuisine cuisine = Cuisine.find(Integer.parseInt(request.queryParams("cuisine_id")));
    String name = request.queryParams("name");
    String city = request.queryParams("city");
    String hours = request.queryParams("hours");
    Restaurant newRestaurant = new Restaurant(name, city, hours, cuisine.getId());
    newRestaurant.save();
    model.put("cuisine", cuisine);
    model.put("cuisines", Cuisine.all());
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/cuisines/:id/update", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
    model.put("cuisine", cuisine);
    model.put("template", "templates/cuisine-form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/cuisines/:id/update", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
    String foodtype = request.queryParams("foodtype");
    cuisine.update(foodtype);
    response.redirect("/cuisines");
    return null;
  });

  post("cuisines/:id/delete", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
    cuisine.delete();
  //  model.put("cuisines", Cuisine.all());
    response.redirect("/cuisines");
    return null;
  });





}//end of m

}//end of app
