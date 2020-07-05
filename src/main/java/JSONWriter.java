import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class puts results into JSONObject and writes to the file
 */
public class JSONWriter {
    public static Logger LOGGER = Logger.getLogger(JSONWriter.class.getName());
    public final String FILENAME;
    public JSONObject object;

    public JSONWriter(String path, JSONObject object) {
        this.FILENAME = path;
        this.object = object;
    }

    public void writeToJSON() {
        try (FileWriter writer = new FileWriter(FILENAME)) {
            object.putAll(MyThread.concurrentHashMap);
            writer.write(object.toJSONString());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "IOException");
            e.printStackTrace();
        }
    }
}
