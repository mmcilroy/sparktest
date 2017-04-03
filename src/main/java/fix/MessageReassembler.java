package fix;

import java.util.function.Consumer;

public class MessageReassembler {

    private final String CHECKSUM = "10=";
    private final String DELIM = "\001";

    private StringBuilder buffer = new StringBuilder();

    public void reassemble(String fragment, Consumer<String> consumer) {

        // add the new fragment to our buffer
        buffer.append(fragment);

        // search for all complete messages and dispatch to consumer
        boolean recheck;
        do {
            recheck = false;
            // check if the checksum field is present
            int ci = buffer.indexOf(CHECKSUM);
            if (ci > 0) {
                // ensure checksum field is complete
                int di = buffer.indexOf(DELIM, ci);
                if (di > 0) {
                    // pass complete message to consumer for processing
                    consumer.accept(buffer.substring(0, di + 1));
                    // remove complete message from buffer
                    buffer.delete(0, di + 1);
                    // there might be more messages in buffer so loop again
                    recheck = true;
                }
            }
        } while (recheck);
    }
}
