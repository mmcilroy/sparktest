package sim;

import fix.Field;
import fix.Message;
import fix.Session;
import fix.SessionId;

import java.util.LinkedList;
import java.util.List;

public class SimSession extends Session {

    private List<Message> messages = new LinkedList<>();

    public SimSession(SessionId id) {
        super(id);
    }

    public void send(String[][] bodyStr) {
        if (bodyStr.length > 0) {
            String type = "?";
            if (bodyStr[0][0].compareTo("35") == 0) {
                type = bodyStr[0][1];
            }
            Field[] body = new Field[bodyStr.length - 1];
            for (int i = 1; i < bodyStr.length; i++) {
                body[i-1] = new Field(Integer.parseInt(bodyStr[i][0]), bodyStr[i][1]);
            }
            messages.add(send(type, body));
        }
    }

    public void receive(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
