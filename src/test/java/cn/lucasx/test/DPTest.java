package cn.lucasx.test;

import cn.lucasx.core.DPSpider;
import cn.lucasx.util.Toolkit;
import cn.mobile.entity.Csv;
import cn.mobile.entity.Merchant;
import cn.mobile.kernel.FileUtil;
import cn.mobile.kernel.MDPSpider;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/2/2.
 */
public class DPTest {

    @Test
    public void testWriteToCsv() throws IOException {
        Csv csv = new Csv();
        csv.setCvsName("游乐");
        List<Merchant> merchantList = new ArrayList<>();
        merchantList.add(new Merchant("武昌区", "欢乐谷111", "光谷", "027-8888888", "22", "333", "游玩", "游乐园", "9.9", "8.8", "8.7", "很好玩呀"));
        merchantList.add(new Merchant("武昌区", "欢乐谷2", "光谷", "027-8888888", "33", "332", "游玩", "游乐园", "9.9", "8.8", "8.7", "很好玩呀"));
        merchantList.add(new Merchant("武昌区", "欢乐谷3", "光谷", "027-8888888", "44", "765", "游玩", "游乐园", "9.9", "8.8", "8.7", "很好玩呀"));
        csv.setList(merchantList);

        FileUtil.writeToCsv(csv, "有一个测试1");
    }


    @Test
    public void testReadXml() throws IOException, SAXException, ParserConfigurationException {
        Toolkit.readTypeXmlConfig("D:\\Users\\DZDPPro\\src\\main\\resources\\type.xml").forEach(city -> System.out.println(city));
    }

    @Test
    public void testDate() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }

}
