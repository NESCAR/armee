package abc.ney.armee.enginee.tool;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 本地properties配置器<br/>
 * 周期性更新
 * @author Charles Song
 * @date 2020-5-26
 */
public class LocalPropConfiger extends PropertiesReader implements Configer, Runnable {

    /**
     * 从内部配置周期性执行线程
     * @param addr 地址
     * @param executor 周期执行器
     * @param unit 单位
     * @param interval 间隔
     */
    public LocalPropConfiger (String addr, ScheduledExecutorService executor, TimeUnit unit, int interval) {
        super(addr);
        executor.schedule(this, interval, unit);
    }

    /**
     * 从外部加入线程周期性执行
     * @param addr 地址
     */
    public LocalPropConfiger (String addr) {
        super(addr);
    }
    @Override
    public void run () {
        updateProp();
    }
    @Override
    public String getProp (String name) {
        return super.readValue(name);
    }
    @Override
    public void updateProp (String addr) {
        super.updateProps (addr);
    }
    @Override
    public void updateProp () {
        super.updateProps();
    }
}
