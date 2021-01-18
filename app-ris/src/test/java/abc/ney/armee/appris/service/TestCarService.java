package abc.ney.armee.appris.service;

import abc.ney.armee.appris.dal.meta.po.Device;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource(value = "classpath:application-ris.properties")
@Slf4j
public class TestCarService {
    CarService carService;
    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }

//    @Test
    public void testUpdateDeviceByImei() {
        Device device = new Device();
        device.setGmtUpdate(new Timestamp(System.currentTimeMillis()));
        device.setImei("123");device.setLockStartTime(Timestamp.valueOf("2021-01-17 14:00:00"));
        device.setLockEndTime(Timestamp.valueOf("2021-01-17 20:00:00"));device.setDriverGid(12L);
        carService.updateDeviceByImei(device);
    }
}
