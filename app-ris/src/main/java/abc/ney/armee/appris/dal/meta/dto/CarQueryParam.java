package abc.ney.armee.appris.dal.meta.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Set;

/**
 * 汽车查询信息
 */
@Setter
@Getter
@ToString
public class CarQueryParam {
    /**
     * 需要查询的数据类型
     */
    private Set<String> fields;
    /**
     * 标签
     */
    private Map<String, String> tags;
    /**
     * 数据保留策略，默认为null即可
     */
    private String retentionPolicy;
    /**
     * 开始时间，如果不加限制可以为null
     */
    private String st;
    /**
     * 结束时间，如果不加限制可以为null
     */
    private String et;
}
