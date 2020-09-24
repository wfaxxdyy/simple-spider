package cn.wf.simplespider;

import cn.wf.simplespider.task.DailySpiderSchedule;
import org.htmlcleaner.XPatherException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: fan.wang
 * @Date: 2020/9/24 11:19
 * @description:
 */
@SpringBootTest
public class SpiderTest {

    @Autowired
    private DailySpiderSchedule spiderSchedule;

    @Test
    public void testDailySpider() throws XPatherException {
        spiderSchedule.getBilibiliVideoInfo();
    }

}
