package cn.wf.simplespider.task;

import cn.wf.simplespider.entity.VideoInfo;
import cn.wf.simplespider.enums.BilibiliSectionEnum;
import cn.wf.simplespider.enums.SourceEnum;
import cn.wf.simplespider.factory.ProcessPageInfoFactory;
import cn.wf.simplespider.factory.Processor.Processor;
import cn.wf.simplespider.model.PageInfo;
import cn.wf.simplespider.service.AnimeService;
import cn.wf.simplespider.service.VideoInfoService;
import org.htmlcleaner.XPatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: fan.wang
 * @Date: 2020/9/21 15:31
 * @description:
 */
@Component
@EnableScheduling
public class DailySpiderSchedule {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private VideoInfoService videoInfoService;

    @Autowired
    private ProcessPageInfoFactory processPageInfoFactory;

    @Scheduled(cron = "0 7 17 * * ?")
    public void getBilibiliVideoInfo() throws XPatherException {
        //全站榜
        String BilibiliUrlPreix = "https://www.bilibili.com/ranking/all/";
        //每日榜
        String BilibiliUrlSuffix = "/0/1";

        for (BilibiliSectionEnum section:BilibiliSectionEnum.values()){
            PageInfo pageInfo = animeService.downloadPageInfo(BilibiliUrlPreix + section.getCode() + BilibiliUrlSuffix);
            Processor processor = processPageInfoFactory.getProcessor(SourceEnum.BILIBILI.getType());
            List<VideoInfo> infoList = processor.processPageInfo(pageInfo, section.getCode());
            videoInfoService.insertBatch(infoList);
        }

    }
}
