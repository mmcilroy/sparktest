import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

public class DataModel {
    public static void init() {
        sessions = new LinkedList<String>();
        sessions.add("FIX.4.4;SESSION1;FGW;");
        sessions.add("FIX.4.4;SESSION2;FGW;");
        sessions.add("FIX.4.4;SESSION3;FGW;");
        sessions.add("FIX.4.4;SESSION4;FGW;");

        messages = new HashMap<String, LinkedList<String>>();
        messages.put("FIX.4.4;SESSION1;FGW;", newStringList(
            "8=FIXT.1.1 | 9=0232 | 35=D | 49=TCITATDFT01 | 56=FGW | 52=20170315-11:23:08.589 | 34=29437 | 22=4 | 48=FR0000133308 | 207=XPAR | 15=EUR | 11=91000000000032730568 | 60=20170315-11:23:08.588 | 54=1 | 453=1 | 448=TCITCLBFG01 | 447=D | 452=76 | 40=2 | 38=61 | 44=14.56 | 1138=61 | 581=3 | 528=R | 59=0 | 10=162",
            "8=FIXT.1.1 | 9=361 | 35=8 | 49=FGW | 56=TCITATDFT01 | 34=46062 | 52=20170316-12:00:02.000 | 1128=9 | 11=91000000000045940266 | 14=0 | 15=EUR | 17=E0Ulu44QrI7G | 22=4 | 37=O0Ulu8uTMzLb | 38=24 | 39=0 | 40=2 | 44=46.25 | 48=NL0000009355 | 54=1 | 55=UNAa | 59=0 | 60=20170316-12:00:02.000 | 110=0 | 150=0 | 151=24 | 198=05C7F64C400DE64F | 207=XAMS | 278=O0Ulu8uTMzLb | 528=R | 581=3 | 1138=24 | 9303=I | 20000=2 | 453=1 | 448=TCITCLBFG01 | 447=D | 452=76 | 10=157",
            "8=FIXT.1.1 | 9=421 | 35=8 | 49=FGW | 56=TCITATDFT01 | 34=46065 | 52=20170316-12:00:02.904 | 1128=9 | 11=91000000000032737210 | 14=34 | 15=EUR | 17=E0Ulu3kVVwfo | 22=4 | 31=14.645 | 32=34 | 37=O0Ulu8aY2CHB | 38=34 | 39=2 | 40=2 | 44=14.645 | 48=NL0011821202 | 54=1 | 55=INGAa | 59=0 | 60=20170316-12:00:02.904 | 110=0 | 150=F | 151=0 | 198=05C7F648000C14E9 | 207=XAMS | 278=O0Ulu8aY2CHB | 528=R | 581=3 | 880=WG9HU34EQV | 1138=0 | 9303=I | 9730=A | 20000=0 | 453=2 | 448=TCITCLBFG01 | 447=D | 452=76 | 448=LCH | 447=D | 452=17 | 10=092"));
    }

    public static LinkedList<String> getSessions() {
        return sessions;
    }

    public static LinkedList<String> getMessagesForSession(String session) {
        return messages.get(session);
    }

    private static LinkedList<String> newStringList(String... strings) {
        LinkedList<String> list = new LinkedList<String>();
        for(String string : strings) {
            list.add(string);
        }
        return list;
    }

    public static LinkedList<String> sessions;
    public static Map<String, LinkedList<String>> messages;
}
