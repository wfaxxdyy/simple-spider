package cn.wf.simplespider.controller;

import cn.wf.simplespider.entity.VideoInfo;
import cn.wf.simplespider.model.PageInfo;
import cn.wf.simplespider.model.Resp;
import cn.wf.simplespider.service.VideoExtendService;
import cn.wf.simplespider.service.VideoInfoService;
import cn.wf.simplespider.utils.PageDownloadUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.htmlcleaner.XPatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: fan.wang
 * @Date: 2020/9/18 11:22
 * @description:
 */

@RestController
@RequestMapping("/page/info")
public class VideoInfoController {

    @Autowired
    private VideoInfoService videoInfoService;

    @Autowired
    private VideoExtendService videoExtendService;

    @RequestMapping("/download/anime")
    public Resp downloadAnime() throws XPatherException {
        String url = "https://www.bilibili.com/ranking/all/0/0/1";
        PageInfo pageInfo = PageDownloadUtil.downloadPageInfo(url);
        videoInfoService.processPageInfo(pageInfo);
        return Resp.success("success!");
    }

    @RequestMapping("/flush/video/extend")
    public Resp flushVideoExtend(){
        List<VideoInfo> infoList = videoInfoService.selectList(new EntityWrapper<VideoInfo>().in("id",2));
        System.out.println(infoList.size());
        videoExtendService.saveVideoExtend2(infoList);
        return Resp.success();
    }

}
