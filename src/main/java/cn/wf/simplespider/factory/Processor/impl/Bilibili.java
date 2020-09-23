package cn.wf.simplespider.factory.Processor.impl;

import cn.wf.simplespider.entity.VideoInfo;
import cn.wf.simplespider.factory.Processor.Processor;
import cn.wf.simplespider.model.PageInfo;
import cn.wf.simplespider.utils.TagNodeUtil;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: fan.wang
 * @Date: 2020/9/21 9:52
 * @description: B站页面处理器
 */
@Component
public class Bilibili implements Processor {


    /**
     * 获取分区每日前十榜单
     * @param pageInfo
     * @param sectionCode
     * @return
     * @throws XPatherException
     */
    @Override
    public List<VideoInfo> processPageInfo(PageInfo pageInfo, Integer sectionCode) throws XPatherException {
        String content = pageInfo.getContent();
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode node = htmlCleaner.clean(content);
        List<VideoInfo> videoInfoList = new ArrayList<>();
        String prefix = "//*[@id=\"app\"]/div[1]/div/div[1]/div[2]/div[3]/ul/";
        for (int i = 1; i <= 10; i++) {
            VideoInfo videoInfo = new VideoInfo();
            //section
            videoInfo.setSection(sectionCode);
            //videoId
            String videoId = TagNodeUtil.getAttributeByName(node, prefix + "li[" + i + "]", "data-id");
            videoInfo.setVideoId(Long.valueOf(videoId));
            // rank
            String rank = TagNodeUtil.getInfoByXPath(node, prefix + "li[" + i + "]/div[1]");
            videoInfo.setRank(Long.valueOf(rank));
            // title
            String title = TagNodeUtil.getInfoByXPath(node, prefix + "li[" + i + "]/div[2]/div[2]/a");
            videoInfo.setTitle(title);
            // url
            String url = TagNodeUtil.getAttributeByName(node, prefix + "li[" + i + "]/div[2]/div[2]/a", "href");
            videoInfo.setUrl(url);
            // pts
            String pts = TagNodeUtil.getInfoByXPath(node, prefix + "li[" + i + "]/div[2]/div[2]/div[2]/div");
            videoInfo.setPts(Long.valueOf(pts));
            // play
            String play = TagNodeUtil.getInfoByXPath(node, prefix + "li[" + i + "]/div[2]/div[2]/div[1]/span[1]");
            videoInfo.setPlay(play);
            // author
            String author = TagNodeUtil.getInfoByXPath(node, prefix + "li[" + i + "]/div[2]/div[2]/div[1]/a/span");
            videoInfo.setAuthor(author);
            // comment
            String comments = TagNodeUtil.getInfoByXPath(node, prefix + "li[" + i + "]/div[2]/div[2]/div[1]/span[2]");
            videoInfo.setComments(comments);
            // day
            videoInfo.setDay(new Date());
            videoInfoList.add(videoInfo);

        }
        return videoInfoList;
    }
}
