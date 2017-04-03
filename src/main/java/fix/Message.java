package fix;

import java.util.Vector;

public class Message {

    private final String DELIM = "\001";

    private String type;
    private Vector<Field> fields = new Vector<>();

    public Message() {
        ;
    }

    public Message(String message) {
        parse(message);
    }

    public String getType() {
        return type;
    }

    public Vector<Field> getFields() {
        return fields;
    }

    public void add(Integer tag, String value) {
        fields.add(new Field(tag, value));
    }

    public void parse(String messageStr) {
        String[] fields = messageStr.split(DELIM);
        for (String field : fields) {
            String[] tagVal = field.split("=");
            if (tagVal.length == 2) {
                Integer tag = Integer.parseInt(tagVal[0]);
                String val = tagVal[1];
                add(tag, val);
                if (tag == 35) {
                    type = val;
                }
            }
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Field field : fields) {
            str.append(field.tag);
            str.append("=");
            str.append(field.value);
            str.append("|");
        }
        return str.toString();
    }
}
