import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Main {

    public static Logger LOGGER;

    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/resources/log.properties"));
            LOGGER = Logger.getLogger(Main.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        LOGGER.log(Level.INFO, "App is started");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("scan -h 91.77.167.200-240 -p 1-30 -t 12");

        /**
         * The host,port and threads count variables are read, processed, and initialized
         */
        String scan = reader.readLine();
        LOGGER.log(Level.INFO, "Cutting host,port and threads count");
        String host = DataParser.hostCutter(scan);
        String port = DataParser.portCutter(scan);
        int threads_count = DataParser.threadCutter(scan);
        reader.close();


        DataParser dp = new DataParser(host, port);
        /**
         * These 2 methods sort hosts and ports into lists
         */
        LOGGER.log(Level.INFO, "Parsing host and port");
        dp.hostParser();
        dp.portParser();

        /**
         * Shuffle the previous 2 received lists
         */
        LOGGER.log(Level.INFO, "Shuffling the list of hosts and ports");
        Collections.shuffle(DataParser.all_Hosts);
        Collections.shuffle(DataParser.all_Ports);

        System.out.println(DataParser.all_Hosts);
        System.out.println(DataParser.all_Ports);

        /**
         * Create ExecutorService and add tasks for scanning the specified host and port
         */
        LOGGER.log(Level.INFO, "Creating ExecutorService");
        ExecutorService es = Executors.newFixedThreadPool(threads_count);
        LOGGER.log(Level.INFO, "Adding tasks to the ExecutorService");
        for (int i = 0; i < DataParser.all_Hosts.size(); i++) {
            for (int j = 0; j < DataParser.all_Ports.size(); j++) {
                es.submit(new MyThread(DataParser.all_Hosts.get(i), DataParser.all_Ports.get(j)));
            }
        }
        LOGGER.log(Level.INFO, "Shutdown the ExecutorService");
        es.shutdown();

        /**
         *  Waiting for threads to complete tasks
         */
        try {
            es.awaitTermination(2, TimeUnit.HOURS);
            LOGGER.log(Level.INFO, "Tasks are done");
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Executor interrupted");
            e.printStackTrace();
        }

        /**
         *  Writing the result in JSON
         */
        JSONWriter jw = new JSONWriter("src\\main\\resources\\JSON_Output", new JSONObject());
        jw.writeToJSON();
    }
}

