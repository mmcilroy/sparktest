package script;

import fix.Session;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class Script {
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        engine.eval(new FileReader("test.js"));

        Invocable inv = (Invocable) engine;
        //inv.invokeFunction("onMessage", new Session());
    }
}
