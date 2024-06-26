package tomcatxpkg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.Maps;

public class YmlUtil {

    /**
     * key:文件名索引
     * value:配置文件内容
     */
    private static Map<String, LinkedHashMap> ymls = new HashMap<>();

    /**
     * string:当前线程需要查询的文件名
     */
    private static ThreadLocal<String> nowFileName = new ThreadLocal<>();

    /**
     * 加载配置文件
     * @param fileName
     */
    public static void loadYml(String fileName) {
        nowFileName.set(fileName);
        if (!ymls.containsKey(fileName)) {
            ymls.put(fileName, new Yaml().loadAs(YmlUtil.class.getResourceAsStream("/" + fileName), LinkedHashMap.class));
        }
    }

    public static Object getValue(String key) throws Exception {
        // 首先将key进行拆分
        String[] keys = key.split("[.]");

        // 将配置文件进行复制
        Map ymlInfo = (Map) ymls.get(nowFileName.get()).clone();
        for (int i = 0; i < keys.length; i++) {
            Object value = ymlInfo.get(keys[i]);
            if (i < keys.length - 1) {
                ymlInfo = (Map) value;
            } else if (value == null) {
                throw new Exception("key不存在");
            } else {
                return value;
            }
        }
        throw new RuntimeException("不可能到这里的...");
    }

    public static Object getValue(String fileName, String key) throws Exception {
        // 首先加载配置文件
        loadYml(fileName);
        return getValue(key);
    }


    public static void main(String[] args) throws Exception {
        System.out.println((getValue("application.yml", "server.port")));
    }

	@SuppressWarnings("rawtypes")
	public static Map getDoc(String ymlString, Predicate<Map> predicate) throws FileNotFoundException {
		Yaml yaml = new Yaml();

		Iterable<Object> result = yaml.loadAll(new FileInputStream(ymlString));
		for (Object obj : result) {
			//System.out.println(obj.getClass()); //java.util.LinkedHashMap
		//	System.out.println(obj);
			if(predicate.test((Map) obj))
				return (Map) obj;
		}
		
	 
		return Maps.newLinkedHashMap();
		
	}
}
