package cn.wf.simplespider.service;

import cn.wf.simplespider.entity.VideoInfo;
import cn.wf.simplespider.enums.BilibiliSectionEnum;
import cn.wf.simplespider.enums.SourceEnum;
import cn.wf.simplespider.factory.ProcessPageInfoFactory;
import cn.wf.simplespider.factory.Processor.Processor;
import cn.wf.simplespider.mapper.VideoInfoMapper;
import cn.wf.simplespider.model.PageInfo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.XPatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wang fan
 * @since 2020-09-18
 */
@Service
public class VideoInfoService extends ServiceImpl<VideoInfoMapper, VideoInfo> {

    @Autowired
    private ProcessPageInfoFactory processPageInfoFactory;

    public void processPageInfo(PageInfo pageInfo) throws XPatherException {
        Processor processor = processPageInfoFactory.getProcessor(SourceEnum.BILIBILI.getType());
        List<VideoInfo> videoInfoList = processor.processPageInfo(pageInfo, BilibiliSectionEnum.TOTAL.getCode());
        insertBatch(videoInfoList);
    }

}
