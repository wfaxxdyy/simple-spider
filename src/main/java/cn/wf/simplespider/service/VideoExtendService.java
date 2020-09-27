package cn.wf.simplespider.service;

import cn.wf.simplespider.entity.VideoExtend;
import cn.wf.simplespider.entity.VideoInfo;
import cn.wf.simplespider.mapper.VideoExtendMapper;
import cn.wf.simplespider.model.PageInfo;
import cn.wf.simplespider.model.resp.VideoExtendResponse;
import cn.wf.simplespider.model.resp.VideoResponse;
import cn.wf.simplespider.utils.PageDownloadUtil;
import cn.wf.simplespider.utils.RandomUtil;
import cn.wf.simplespider.utils.TagNodeUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: fan.wang
 * @Date: 2020/9/24 17:17
 * @description: videoExtend
 */

@Slf4j
@Service
public class VideoExtendService extends ServiceImpl<VideoExtendMapper, VideoExtend> {

    /**
     * 无法通过dom进行解析页面，视频页面被反爬虫
     *
     * @param data
     */
    @Deprecated
    public void saveVideoExtend(List<VideoInfo> data) {
        //待更新视频扩展表集合
        List<VideoExtend> videoExtendList = new ArrayList<>();
        //线程池
        ExecutorService poolExecutor = Executors.newFixedThreadPool(100);
        //下载与解析页面
        for (VideoInfo videoInfo : data) {
            poolExecutor.execute(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String url = videoInfo.getUrl();
                PageInfo pageInfo = PageDownloadUtil.downloadPageInfo(url);
                VideoExtend videoExtend = processBilibiliExtend(pageInfo, videoInfo.getVideoId());
                videoExtendList.add(videoExtend);
                if (videoExtendList.size() == data.size()) {
                    insertBatch(videoExtendList);
                }
            });
        }
        PageInfo pageInfo = PageDownloadUtil.downloadPageInfo(data.get(0).getUrl());
        VideoExtend videoExtend = processBilibiliExtend(pageInfo, data.get(0).getVideoId());
        insert(videoExtend);
    }

    /**
     * 无法通过dom进行解析页面，视频页面被反爬虫
     *
     * @param pageInfo
     * @param videoId
     * @return
     */
    @Deprecated
    private VideoExtend processBilibiliExtend(PageInfo pageInfo, Long videoId) {
        String content = pageInfo.getContent();
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode node = htmlCleaner.clean(content);

        String prefix = "//*[@id=\"arc_toolbar_report\"]/div[1]";
        VideoExtend videoExtend = new VideoExtend();
        videoExtend.setVideoId(videoId);
        String like = TagNodeUtil.getInfoByXPath(node, prefix + "/span[1]");
        videoExtend.setLike(Long.valueOf(like));
        String coin = TagNodeUtil.getInfoByXPath(node, prefix + "/span[2]");
        System.out.println(coin);
        videoExtend.setCoin(Long.valueOf(coin));
        String favorite = TagNodeUtil.getInfoByXPath(node, prefix + "/span[3]");
        videoExtend.setFavorite(Long.valueOf(favorite));
        String share = TagNodeUtil.getInfoByXPath(node, prefix + "/span[4]");
        videoExtend.setShare(Long.valueOf(share));
        String videoTime = TagNodeUtil.getInfoByXPath(node, "//*[@id=\"viewbox_report\"]/div/span[3]");
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(videoTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (date==null){
//            System.out.println(videoId);
//        }
//        videoExtend.setVideoTime(date);
        return videoExtend;
    }

    /**
     * 通过restTemplate获取视频扩展信息
     *
     * @param data
     */
    public int saveVideoExtend2(List<VideoInfo> data) {
        //RestTemplate发送请求
        RestTemplate restTemplate = new RestTemplate();
        //请求前缀
        String prefix = "https://api.bilibili.com/x/web-interface/archive/stat?aid=";
        //userAgent
        String userAgent =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36";
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", userAgent);
        //待更新视频扩展表集合
        List<VideoExtend> videoExtendList = Collections.synchronizedList(new ArrayList<>());
        //线程池
        ExecutorService poolExecutor = Executors.newFixedThreadPool(10);
        for (VideoInfo videoInfo : data) {
            poolExecutor.execute(() -> {
                try {
                    Thread.sleep(1000 * RandomUtil.getRandomInt(1, 10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String url = prefix + videoInfo.getVideoId();
                ResponseEntity<VideoResponse> response = null;
                try {
                    response = restTemplate.exchange(url, HttpMethod.GET,
                            new HttpEntity<String>(headers), VideoResponse.class);
                } catch (Exception e) {
                    log.error("视频{}获取页面失败，原因如下：" + e.getMessage(),videoInfo.getVideoId());
                }
                VideoResponse videoResponse = response.getBody();
                VideoExtend videoExtend = buildVideoExtend(videoResponse, videoInfo);
                if (videoExtend != null) {
                    videoExtendList.add(videoExtend);
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        poolExecutor.shutdown();
        while (true) {//等待所有任务都执行结束
            if (poolExecutor.isTerminated()) {//所有的子线程都结束了
                insertBatch(videoExtendList);
                log.info("本次成功插入VideoExtend数据总计：{}条", videoExtendList.size());
                break;
            }
        }
        return videoExtendList.size();
    }

    /**
     * 构建VideoExtend对象
     *
     * @param videoResponse
     * @param videoInfo
     * @return
     */
    private VideoExtend buildVideoExtend(VideoResponse videoResponse, VideoInfo videoInfo) {
        if (videoResponse == null || videoResponse.getData() == null) {
            log.error("视频id为：" + videoInfo.getVideoId() + "，不存在！！！");
            return null;
        }
        VideoExtendResponse videoExtendResponse = videoResponse.getData();
        VideoExtend videoExtend = new VideoExtend();
        BeanUtils.copyProperties(videoExtendResponse, videoExtend);
        videoExtend.setVideoId(videoInfo.getVideoId());
        return videoExtend;
    }
}
