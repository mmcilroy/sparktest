package sim;

import fix.SessionId;
import fix.netty.Acceptor;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.staticFiles;

public class Main {
    public static void main(String[] args) throws Exception {

        SimSessionFactory factory = new SimSessionFactory();

        staticFiles.location("/static"); // Static files

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.vm");
        }, new VelocityTemplateEngine());

        get("/sessions", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("data", factory.getSessionIds());
            return new ModelAndView(model, "jsonArray.vm");
        }, new VelocityTemplateEngine());

        get("/messages", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            SessionId id = new SessionId(req.queryParams("session"));
            model.put("data", ((SimSession) factory.getSession(id)).getMessages());
            return new ModelAndView(model, "jsonArray.vm");
        }, new VelocityTemplateEngine());

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.eval(new java.io.FileReader("script.js"));

        Acceptor acceptor = new Acceptor(factory, "localhost", 9100);

        acceptor.onConnect((session) -> {
            System.out.println("Connected: " + session.getSessionId());
            Invocable inv = (Invocable) engine;
            try {
                inv.invokeFunction("onConnect", session);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });

        acceptor.onReceive((session, message) -> {
            System.out.println("Received: " + session.getSessionId() + " - " + message);
            session.receive(message);
            Invocable inv = (Invocable) engine;
            try {
                inv.invokeFunction("onReceive", session, message);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });

        acceptor.run();
    }
}
