package abc.ney.armee.enginee.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * properties读取工具
 * @author Charles Song
 * @date 2020-5-26
 */
public class PropertiesReader implements Serializable {
    private static final long serialVersionUID = 1228869787738549194L;
    private static final int MAX_PROPERTIES_MAP_CAP = 100;
    private static final String PROPERTIES_SEPERATOR = ",";
    private static final String KEY_VAL_SEPERATOR = ":";
    private static final int PATH_LOCK_WAIT_MS_TIME = 50;
    /**
     * properties
     */
    private Properties props=null;

    /**
     * file path
     */
    private String path;

    private Lock pathLock;

    /**
     * last modified time
     */
    private long lastModified;

    /**
     * load properties file
     */
    public PropertiesReader(String path){
        this.pathLock = new ReentrantLock();
        this.updateProps(path);
    }

    /**
     * read property
     * @param key key
     * @return value
     */
    public String readValue(String key) {
        return  props.getProperty(key);
    }

    /**
     * 更新属性
     * @param path properties file
     */
    public void updateProps (String path) {
        try {
            if (pathLock.tryLock(PATH_LOCK_WAIT_MS_TIME, TimeUnit.MICROSECONDS)) {
                this.path = path;
                this.updateProps();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pathLock.unlock();
        }

    }
    /**
     * 获取属性
     * @return
     */
    public void updateProps (){
        try{
            if (pathLock.tryLock(PATH_LOCK_WAIT_MS_TIME, TimeUnit.MICROSECONDS)) {
                File file = new File(this.path);
                long modifiedTime = file.lastModified();
                if (this.lastModified != modifiedTime) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    props = new Properties();
                    props.load(bufferedReader);
                    bufferedReader.close();
                }
            }
        }catch (Exception e) {
            System.err.println(e);
        } finally {
            pathLock.unlock();
        }
    }
    /**
     * 获取props
     * @return
     */
    public Properties getProps () {
        return props;
    }

    /**
     * trans json format properties to map
     * @param properties json format properties <br/> such as {val1:0.12,val2:2.2}
     * @return properties Map
     */
    public Map<String, String > getPropertiesMap (String properties) {
        Map<String, String> map = new HashMap<>(MAX_PROPERTIES_MAP_CAP);
        properties = properties.trim();
        // rm "{" and "}" and " "
        properties = properties.replaceAll("[\\{|\\}| ]","");
        // split
        String[] propertisArray = properties.split(PROPERTIES_SEPERATOR);
        for (String kv : propertisArray) {
            String[] kvArray = kv.split(KEY_VAL_SEPERATOR,2);
            map.put(kvArray[0],kvArray[1]);
        }
        return map;
    }

    /**
     * trans json format properties to list
     * @param properties json format properties <br/> such as {0.12,2.2}
     * @return String[]
     */
    public String[] getPropertiesList (String properties) {
        properties = properties.trim();
        // rm "{" and "}" and " "
        properties = properties.replaceAll("[\\{|\\}| ]","");
        // split
        return properties.split(PROPERTIES_SEPERATOR);
    }

}
