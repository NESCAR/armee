package abc.ney.armee.appris.dal.mapper.tsmap;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Map和消息的转化器
 * @author neyzoter
 */
public class MapMsgConvertUtils {
    /**
     * map转化为Object
     * @param map map
     * @param beanClass 转化后的对象类型
     * @return 对象
     * @throws Exception 异常
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        String jsonStr = JSONObject.toJSONString(map);
        return JSONObject.parseObject(jsonStr, beanClass);
    }

    /**
     * 将object转化为json
     * @param obj 序列化对象
     * @return Json
     */
    public static String objectToJson(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    /**
     * json转化为对象
     * @param json json
     * @param c 类型
     * @return 对象
     */
    public static Object jsonToObject(String json, Class<?> c) {
        return JSONObject.parseObject(json, c);
    }

    /**
     * Object转化为map
     * @param obj 转化前的对象
     * @return 转化后的map
     */
    public static Map<String, Object> objectToMap(Object obj) {
        String jsonStr = JSONObject.toJSONString(obj);
        return JSONObject.parseObject(jsonStr);
    }
}
