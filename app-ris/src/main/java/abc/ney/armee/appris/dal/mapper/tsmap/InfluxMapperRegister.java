package abc.ney.armee.appris.dal.mapper.tsmap;

import io.github.hylexus.jt.data.msg.MsgType;
import io.github.hylexus.jt808.support.MsgHandlerMapping;
import lombok.NonNull;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 时序数据库映射器登记
 * 根据msgtype去拿到对应的mapper（然后根据不同的数据进行数据库的增删改查操作）
 * @author neyzoter
 */
@Component
@ToString
public class InfluxMapperRegister {
    private static final Logger log = LoggerFactory.getLogger(MsgHandlerMapping.class);
    private final Map<Integer, InfluxMapper> register;
    private Supplier<InfluxMapper> defaultInfluxMapperSupplier;

    public InfluxMapperRegister() {
        this.register = new ConcurrentHashMap<>();
    }

    private boolean containsMapper(@NonNull MsgType msgType) {
        if (msgType == null) {
            throw new NullPointerException("MsgKey is marked non-null but is null");
        } else {
            return this.register.containsKey(msgType.getMsgId());
        }
    }

    public InfluxMapperRegister registerMapper(@NonNull MsgType msgType, @NonNull InfluxMapper mapper) {
        return this.registerMapper(msgType, mapper, false);
    }

    public InfluxMapperRegister registerMapper(@NonNull MsgType msgType, @NonNull InfluxMapper mapper, boolean forceOverride) {
        if (msgType == null) {
            throw new NullPointerException("msgType is marked non-null but is null");
        } else if (mapper == null) {
            throw new NullPointerException("handler is marked non-null but is null");
        } else {
            if (!register.containsKey(msgType.getMsgId())) {
                register.put(msgType.getMsgId(), mapper);
            } else {
                InfluxMapper old = register.get(msgType.getMsgId());
                if (!forceOverride) {
                    log.info("Duplicate MsgType  [{}] with [{}], the MsgHandler [{}] register is skipped.", msgType, old.getClass(), mapper);
                } else {
                    log.warn("Duplicate MsgType : {}, the MsgHandler [{}] was replaced by {}", msgType, old.getClass(), mapper);
                    register.put(msgType.getMsgId(), mapper);
                }
            }
        }
        return this;
    }

    public Optional<InfluxMapper> getMapper(Integer msgId) throws Exception {
        if (msgId == null) {
            throw new Exception("Msg Id Null");
        }
        InfluxMapper mapper = this.register.get(msgId);
        if (mapper != null) {
            return Optional.of(mapper);
        } else {
            return this.defaultInfluxMapperSupplier == null ? Optional.empty() : Optional.ofNullable(this.defaultInfluxMapperSupplier.get());
        }
    }
    public Optional<InfluxMapper> getMapper(MsgType msgType) throws Exception {
        return getMapper(msgType.getMsgId());
    }
    public Map<Integer, InfluxMapper> getHandlerMappings() {
        return Collections.unmodifiableMap(this.register);
    }

    public void setDefaultMsgHandlerSupplier(Supplier<InfluxMapper> supplier) {
        this.defaultInfluxMapperSupplier = supplier;
    }
}
