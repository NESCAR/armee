package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.dal.mapper.tms.LockAuthInfoMapper;
import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;
import abc.ney.armee.appris.service.LockInfoManService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LockInfoManServiceImpl implements LockInfoManService {
    @Resource
    LockAuthInfoMapper lockAuthInfoMapper;


    @Override
    public Boolean covered(LockAuthInfo lockAuthInfo) {
        // 时间范围是否已经覆盖
        return lockAuthInfoMapper.countCoveredInfo(lockAuthInfo) > 0;
    }


    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.SERIALIZABLE)
    @Override
    public Boolean addLockAuthInfo(LockAuthInfo lockAuthInfo) {
        if (covered(lockAuthInfo)) {
            return false;
        }
        if (lockAuthInfoMapper.insert(lockAuthInfo) == ServiceConstant.MYSQL_OP_ERR_RTN) {
            return false;
        }
        return true;
    }

    @Override
    public List<LockAuthInfo> findDownloadInfo() {
        return lockAuthInfoMapper.selectDownloadInfoOfDevices();
    }
}
