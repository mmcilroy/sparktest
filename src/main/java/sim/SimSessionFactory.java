package sim;

import fix.SessionFactory;
import fix.SessionId;
import fix.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimSessionFactory implements SessionFactory {

    private Map<String, Session> sessions = new HashMap<>();

    public Session getSession(SessionId id) {
        Session session = sessions.get(id.toString());
        if (session == null) {
            session = new SimSession(id);
            sessions.put(id.toString(), session);
        }
        return session;
    }

    public Set<String> getSessionIds() {
        return sessions.keySet();
    }
}
