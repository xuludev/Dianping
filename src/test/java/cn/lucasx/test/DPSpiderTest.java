package cn.lucasx.test;

import cn.lucasx.core.DPSpider;
import cn.lucasx.util.Config;
import cn.lucasx.util.Toolkit;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by LucasX on 2016/2/4.
 */
public class DPSpiderTest {


    @Test
    public void testReadConfig() {
        Config config = Toolkit.readConfig("config");
        System.out.println(config);
    }
}
