package fix;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Session {

    private final String DELIM = "\001";
    private static final String TIMEZONE = "GMT";
    private static final String SEND_TIME_FORMAT = "yyyyMMdd-HH:mm:ss.SSS";

    private SessionId id;
    private Transport transport;
    private Integer sendSequence;
    private Integer recvSequence;
    private final SimpleDateFormat sendTimeFormat;

    public Session(SessionId id) {
        this.id = id;
        this.sendSequence = 1;
        this.recvSequence = 1;
        this.sendTimeFormat = new SimpleDateFormat(SEND_TIME_FORMAT);
    }

    public SessionId getSessionId() {
        return id;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Transport getTransport() {
        return transport;
    }

    public Message send(String type, Field[] bodyFields) {
        StringBuilder body = new StringBuilder();
        if (bodyFields != null) {
            for (Field field : bodyFields) {
                body.append(field.tag + "=" + field.value + DELIM);
            }
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        StringBuilder header = new StringBuilder();
        header.append("35=" + type + DELIM);
        header.append("34=" + sendSequence + DELIM);
        header.append("49=" + id.getSender() + DELIM);
        header.append("56=" + id.getTarget() + DELIM);
        header.append("52=" + sendTimeFormat.format(calendar.getTime()) + DELIM);
        header.append(body);

        StringBuilder message = new StringBuilder();
        message.append("8=" + id.getProtocol() + DELIM);
        message.append("9=" + header.length() + DELIM);
        message.append(header);

        int checksum = 0;
        for (int i = 0; i < message.length(); i++) {
            checksum += (byte) message.charAt(i);
            checksum %= 256;
        }
        message.append("10=" + String.format("%03d", checksum) + DELIM);

        sendSequence++;

        if (transport != null) {
            transport.send(message.toString());
        }

        return new Message(message.toString());
    }

    public void receive(Message message) {

    }

    public void confirmReceipt(Integer sequence) {

    }

    public void close() {

    }
}
