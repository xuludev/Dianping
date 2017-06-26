package cn.mobile.kernel;

import cn.lucasx.entity.City;
import cn.lucasx.util.Config;
import cn.lucasx.util.Toolkit;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Created by LucasX on 2016/2/15.
 */
public class MultiThreadSpiderTwo implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        MDPSpider mdpSpider = new MDPSpider();
        Config config = Toolkit.readConfig("config");
        try {
            mdpSpider.process(config.getCityXmlTwo(),
                    config.getTypeXml());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
