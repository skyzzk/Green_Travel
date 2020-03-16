package api.app.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 返回加载文件的数据
 * @author sky
 *
 */
public class LoadFile {

	public static String getProperty(String path, String name){
        String result = "";
        InputStream in = LoadFile.class.getClassLoader().getResourceAsStream(path);
        Properties prop = new Properties();
        try {
            prop.load(in);
            result = prop.getProperty(name).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
