package cn.wf.simplespider.task;

import cn.wf.simplespider.entity.VideoInfo;
import cn.wf.simplespider.enums.BilibiliSectionEnum;
import cn.wf.simplespider.enums.SourceEnum;
import cn.wf.simplespider.factory.ProcessPageInfoFactory;
import cn.wf.simplespider.factory.Processor.Processor;
import cn.wf.simplespider.model.PageInfo;
import cn.wf.simplespider.service.VideoExtendService;
import cn.wf.simplespider.service.VideoInfoService;
import cn.wf.simplespider.utils.PageDownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.XPatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: fan.wang
 * @Date: 2020/9/21 15:31
 * @description: 定时爬虫B站日榜视频
 */
@Slf4j
@Component
@EnableScheduling
public class DailySpiderSchedule {

    @Autowired
    private VideoInfoService videoInfoService;

    @Autowired
    private VideoExtendService videoExtendService;

    @Autowired
    private ProcessPageInfoFactory processPageInfoFactory;

    @Scheduled(cron = "0 53 16 * * ?")
    public void getBilibiliVideoInfo() {
        //全站榜
        String BilibiliUrlPreix = "https://www.bilibili.com/ranking/all/";
        //每日榜
        String BilibiliUrlSuffix = "/0/1";
        List<VideoInfo> data = Collections.synchronizedList(new ArrayList<>(140));
        //创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(20);
        //查询各分区榜单数据
        for (BilibiliSectionEnum section : BilibiliSectionEnum.values()) {
            pool.execute(() -> {
                PageInfo pageInfo = PageDownloadUtil.downloadPageInfo(BilibiliUrlPreix + section.getCode() + BilibiliUrlSuffix);
                if (pageInfo == null) {
                    log.error("每日{}排行榜页面下载失败！！！" + section.getMsg());
                }
                Processor processor = processPageInfoFactory.getProcessor(SourceEnum.BILIBILI.getType());
                List<VideoInfo> infoList = null;
                try {
                    infoList = processor.processPageInfo(pageInfo, section.getCode());
                } catch (XPatherException e) {
                    log.error("{}页面解析失败!!!，错误信息：" + e.getMessage(), section.getMsg());
                }
                data.addAll(infoList);
                //判断数据是否全部加载完毕，完毕后存入数据库
                if (data.size() == 140) {
                    log.info("数据插入开始！！！");
                    videoInfoService.insertBatch(data);
                    log.info("本次成功插入VideoInfo数据总计{}条", data.size());
                    videoExtendService.saveVideoExtend2(data);
                }
            });
        }
    }
}
