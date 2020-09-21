package cn.wf.simplespider.controller;

import cn.wf.simplespider.model.PageInfo;
import cn.wf.simplespider.model.Resp;
import cn.wf.simplespider.service.AnimeService;
import org.htmlcleaner.XPatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fan.wang
 * @Date: 2020/9/18 11:22
 * @description:
 */

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @RequestMapping("/download/pageinfo")
    public Resp downloadPageInfo() throws XPatherException {
        String url = "https://www.bilibili.com/ranking/all/0/0/1";
        PageInfo pageInfo = animeService.downloadPageInfo(url);
        animeService.processPageInfo(pageInfo);
        return Resp.success("success!");
    }

}
