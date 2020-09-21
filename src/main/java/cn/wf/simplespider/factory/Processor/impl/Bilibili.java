package cn.wf.simplespider.factory.Processor.impl;

import cn.wf.simplespider.entity.VideoInfo;
import cn.wf.simplespider.factory.Processor.Processor;
import cn.wf.simplespider.model.PageInfo;
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
 * @description:
 */
@Component
public class Bilibili implements Processor {


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
            // rank
            Object[] ranks = node.evaluateXPath(prefix + "li[" + i + "]/div[1]");
            TagNode rank = (TagNode) ranks[0];
            videoInfo.setRank(Long.valueOf(rank.getText().toString()));
            // title
            Object[] titles = node.evaluateXPath(prefix + "li[" + i + "]/div[2]/div[2]/a");
            TagNode title = (TagNode) titles[0];
            videoInfo.setTitle(title.getText().toString());
            // ptss
            Object[] ptss = node.evaluateXPath(prefix + "li[" + i + "]/div[2]/div[2]/div[2]/div");
            TagNode pts = (TagNode) ptss[0];
            videoInfo.setPts(Long.valueOf(pts.getText().toString()));
            // play
            Object[] plays = node.evaluateXPath(prefix + "li[" + i + "]/div[2]/div[2]/div[1]/span[1]");
            TagNode play = (TagNode) plays[0];
            videoInfo.setPlay(play.getText().toString());
            // author
            Object[] authors = node.evaluateXPath(prefix + "li[" + i + "]/div[2]/div[2]/div[1]/a/span");
            TagNode author = (TagNode) authors[0];
            videoInfo.setAuthor(author.getText().toString());
            // comments
            Object[] comments = node.evaluateXPath(prefix + "li[" + i + "]/div[2]/div[2]/div[1]/span[2]");
            TagNode comment = (TagNode) comments[0];
            videoInfo.setComments(comment.getText().toString());
            //日期
            videoInfo.setDay(new Date());
            videoInfoList.add(videoInfo);

        }
        return videoInfoList;
    }
}
