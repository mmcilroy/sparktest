import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.staticFiles;

public class Main {
    public static void main(String[] args) {
        DataModel.init();

        staticFiles.location("/static"); // Static files

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.vm");
        }, new VelocityTemplateEngine());

        get("/sessions", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("data", DataModel.getSessions());
            return new ModelAndView(model, "jsonArray.vm");
        }, new VelocityTemplateEngine());

        get("/messages", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("data", DataModel.getMessagesForSession(req.queryParams("session")));
            return new ModelAndView(model, "jsonArray.vm");
        }, new VelocityTemplateEngine());
    }
};
