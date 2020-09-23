package cn.wf.simplespider.factory.Processor;

import cn.wf.simplespider.entity.VideoInfo;
import cn.wf.simplespider.model.PageInfo;
import org.htmlcleaner.XPatherException;

import java.util.List;

/**
 * @Author: fan.wang
 * @Date: 2020/9/21 9:53
 * @description: 页面处理器接口
 */
public interface Processor {

    List<VideoInfo> processPageInfo(PageInfo pageInfo, Integer sectionCode) throws XPatherException;

}
