package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.dal.mapper.tms.LockAuthInfoMapper;
import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;
import abc.ney.armee.appris.service.LockInfoManService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

@Service
public class LockInfoManServiceImpl implements LockInfoManService {
    @Resource
    LockAuthInfoMapper lockAuthInfoMapper;


    @Override
    public boolean covered(Long deviceId, Timestamp st, Timestamp et) {
        LockAuthInfo lockAuthInfo = new LockAuthInfo();
        lockAuthInfo.setDeviceId(deviceId);
        lockAuthInfo.setStartTime(st);
        lockAuthInfo.setEndTime(et);
        // 时间范围覆盖了
        if (lockAuthInfoMapper.countCoveredInfo(lockAuthInfo) > 0) {
            return true;
        }
        return false;
    }
}
