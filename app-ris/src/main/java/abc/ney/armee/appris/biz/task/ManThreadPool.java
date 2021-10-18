package abc.ney.armee.appris.biz.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 管理线程池
 * @author neyzoter
 */
@Component
public class ManThreadPool {
    public final int CORE_POOL_SIZE = 1;
    public final int INITIAL_DELAY_MIN_FOR_LOCK_AUTH = 10;
    public final int PERIOD_MIN_FOR_LOCK_AUTH = 30;

    @Autowired
    public ManThreadPool(LockAuthDownTask lockAuthDownTask) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        //定时的线程池
        ses.scheduleAtFixedRate(lockAuthDownTask, INITIAL_DELAY_MIN_FOR_LOCK_AUTH,
                PERIOD_MIN_FOR_LOCK_AUTH, TimeUnit.SECONDS);



    }
}
