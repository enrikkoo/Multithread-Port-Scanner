import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataParser {

    /**
     * These variables store unprocessed strings with information about hosts and ports
     */
    private final String host;
    private final String port;

    public static Logger LOGGER = Logger.getLogger(DataParser.class.getName());

    /**
     * All hosts and ports are stored in these lists
     */
    public final static ArrayList<String> all_Hosts = new ArrayList<>();
    public final static ArrayList<Integer> all_Ports = new ArrayList<>();

    DataParser(String host, String port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method processes hosts and adds them to the list all_Hosts
     */
    public void hostParser() {
        String[] preHost = host.split(",");
        for (int i = 0; i < preHost.length; i++) {
            preHost[i] = preHost[i].trim();
        }
        for (String value : preHost) {
            if (!value.contains("-")) {
                all_Hosts.add(value);
                LOGGER.log(Level.INFO, "hostParser is finished ");
            } else {
                host_range_parser(value);
            }
        }
    }

    private void host_range_parser(String str) {
        String[] host = str.split("\\.");
        String[] last_host_element = host[host.length - 1].split("-");
        for (int j = Integer.parseInt(last_host_element[0]); j <= Integer.parseInt(last_host_element[1]); j++) {
            String ready = host[0] + "." + host[1] + "." + host[2] + "." + j;
            all_Hosts.add(ready);
        }
        LOGGER.log(Level.INFO, "hostParser is finished ");
    }

    /**
     * This method processes ports and adds them to the list all_Ports
     */
    public void portParser() {
        String[] prePort = port.split(",");
        for (int i = 0; i < prePort.length; i++) {
            prePort[i] = prePort[i].trim();
        }
        for (String value : prePort) {
            if (!value.contains("-")) {
                all_Ports.add(Integer.parseInt(value));
                LOGGER.log(Level.INFO, "portParser is finished ");
            } else {
                port_range_parser(value);
            }
        }
    }

    private void port_range_parser(String str) {
        String[] port_element = str.split("-");
        for (int j = Integer.parseInt(port_element[0]); j <= Integer.parseInt(port_element[1]); j++) {
            all_Ports.add(j);
        }
        LOGGER.log(Level.INFO, "portParser is finished ");
    }

    /**
     * This method cuts everything associated with the hosts from input string
     *
     * @param s - input string
     * @return Substring about hosts
     */
    public static String hostCutter(String s) {
        return s.substring(8, (s.indexOf("p") - 2));
    }

    /**
     * This method cuts everything associated with the ports from input string
     *
     * @param s - input string
     * @return Substring about ports
     */
    public static String portCutter(String s) {
        int lastIndex = s.length();
        if (s.contains("t")) {
            lastIndex = s.indexOf("t") - 2;
        }
        return s.substring(s.indexOf("p") + 2, lastIndex);
    }

    /**
     * This method cuts everything associated with threads count from input string
     *
     * @param s - input string
     * @return Substring about threads count
     */
    public static int threadCutter(String s) {
        return Integer.parseInt(s.substring(s.indexOf("t") + 2));
    }
}