package fix;

public class SessionId {

    private String protocol = null;
    private String sender = null;
    private String target = null;
    private String id;

    public SessionId(String id) {
        String[] parts = id.split(";");
        this.protocol = parts[0];
        this.sender = parts[1];
        this.target = parts[2];
        this.id = protocol + ";" + sender + ";" + target;
    }

    public SessionId(String protocol, String sender, String target) {
        this.protocol = protocol;
        this.sender = sender;
        this.target = target;
        this.id = protocol + ";" + sender + ";" + target;
    }

    public SessionId(Message message) {
        for (Field field : message.getFields()) {
            if (field.tag == 8) {
                protocol = field.value;
            } else if (field.tag == 49) {
                sender = field.value;
            } else if (field.tag == 56) {
                target = field.value;
            }
            if (protocol != null && sender != null && target != null) {
                break;
            }
        }
        this.id = protocol + ";" + sender + ";" + target;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getSender() {
        return sender;
    }

    public String getTarget() {
        return target;
    }

    public String toString() {
        return id;
    }
}
