package cn.mobile.kernel;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LucasX on 2016/4/27.
 */
public class DataUnitToolkit {

    /**
     * union all data of one city
     *
     * @param cityDirPath
     * @throws IOException
     */
    public static StringBuilder union(String cityDirPath) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        Files.list(Paths.get(cityDirPath)).forEach(path -> {
            try {
                Files.readAllLines(path, Charset.forName("GBK")).forEach(s -> {
                    System.out.println(s);
                    stringBuilder.append(s);
                    stringBuilder.append("\r\n");
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return stringBuilder;
    }

    private static void write(StringBuilder dataString, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
        }

        BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(filePath), Charset.forName("GBK"));
        bufferedWriter.write(dataString.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static void main(String[] args) {
        StringBuilder stringBuilder;
        try {
            stringBuilder = union("D:\\大众点评\\厦门");
            write(stringBuilder, "D:\\大众点评\\厦门\\厦门.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
