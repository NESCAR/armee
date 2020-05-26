package abc.ney.armee.enginee.tool;


/**
 * 配置器
 * @author Charles Song
 * @date 2020-5-26
 */
public interface Configer {

    /**
     * 获取某个参数
     * @param name 参数名称
     * @return 参数
     */
    String getProp(String name);
    /**
     * 设置配置器读取地址
     * @param addr 参数地址
     */
    void updateProp (String addr);
    /**
     * 更新参数(如果自动更新，则可以忽略)
     */
    void updateProp();
}
