import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DataParserTest {

    DataParser dp;

    @Test
    public void hostCutterTest() {
        String s = "scan -h 91.77.167.200-240 -p 1-30 -t 12";
        String result = DataParser.hostCutter(s);

        Assert.assertEquals("91.77.167.200-240", result);
    }

    @Test
    public void portCutterTest() {
        String s = "scan -h 91.77.167.200-240 -p 1-30 -t 12";
        String result = DataParser.portCutter(s);

        Assert.assertEquals("1-30", result);
    }

    @Test
    public void threadCutterTest() {
        String s = "scan -h 91.77.167.200-240 -p 1-30 -t 12";
        int result = DataParser.threadCutter(s);

        Assert.assertEquals(12, result);
    }

    @Test
    public void hostParserTest() {
        dp = new DataParser("91.77.167.200-201", "-p 1-30");
        dp.hostParser();
        List<String> exp = Arrays.asList("91.77.167.200", "91.77.167.201");
        Assert.assertEquals(exp, DataParser.all_Hosts);
    }

    @Test
    public void portParserTest() {
        dp = new DataParser("91.77.167.200-201", "18-23");
        dp.portParser();
        List<Integer> exp = Arrays.asList(18, 19, 20, 21, 22, 23);
        Assert.assertEquals(exp, DataParser.all_Ports);
    }

    @Test
    public void MyThreadTest() {
        MyThread mt = new MyThread("91.77.167.233", 25);
        Thread t = new Thread(mt);
        t.start();
    }

}
