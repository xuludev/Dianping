package cn.mobile.kernel;

import cn.mobile.entity.Csv;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.LogManager;

/**
 * Created by Administrator on 2016/2/2.
 */
public class FileUtil {

    private static Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public static void writeToCsv(Csv cvs, String cityName) throws IOException {
        if (!Files.exists(Paths.get("D:/大众点评/" + cityName + "/" + cvs.getCvsName() + ".csv"))) {
            if (!Files.exists(Paths.get("D:/大众点评/" + cityName))) {
                Files.createDirectories(Paths.get("D:/大众点评/" + cityName));
            }
            Files.createFile(Paths.get("D:/大众点评/" + cityName + "/" + cvs.getCvsName() + ".csv"));
        } else {
            Files.delete(Paths.get("D:/大众点评/" + cityName + "/" + cvs.getCvsName() + ".csv"));
            logger.debug("处理完成...");
            Files.createFile(Paths.get("D:/大众点评/" + cityName + "/" + cvs.getCvsName() + ".csv"));
        }
        StringBuilder stringBuilder = new StringBuilder("地区,商户,地址,联系电话,点评数量,人均价格,商户大类,商户小类,score1,score2,score3,其他信息\n");
        cvs.getList().forEach(merchant -> {
            stringBuilder.append(merchant.getZone()).append(",").append(merchant.getShopName()).append(",").append
                    (merchant.getAddress()).append(",").append(merchant.getPhone()).append(",").append(merchant.getCommentNum()).append(",").append(merchant.getPrice()).append(",").append(merchant.getMainType()).append(",").append(merchant
                    .getSubType()).append(",").append(merchant.getScore1()).append(",").append(merchant.getScore2())
                    .append(",").append(merchant.getScore3()).append(",").append(merchant.getOtherInfo()).append("\n");
        });
        Files.write(Paths.get("D:/大众点评/" + cityName + "/" + cvs.getCvsName() + ".csv"), stringBuilder.toString().getBytes("GBK"));
    }

}
