package cn.wf.simplespider.service;

import cn.wf.simplespider.entity.Anime;
import cn.wf.simplespider.enums.SourceEnum;
import cn.wf.simplespider.mapper.AnimeMapper;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wang fan
 * @since 2020-09-18
 */
@Service
public class AnimeService extends ServiceImpl<AnimeMapper, Anime> {

    private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36";


    public PageInfo downloadPageInfo(String url) {
        PageInfo pageInfo = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", userAgent);
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                //创建页面对象
                pageInfo = buildPageInfo(responseEntity, url);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pageInfo;
    }

    private PageInfo buildPageInfo(HttpEntity responseEntity, String url) throws IOException {
        PageInfo pageInfo = new PageInfo();
        if(url.contains(SourceEnum.BILIBILI.getDomain())){
            pageInfo.setSource(SourceEnum.BILIBILI.getSource());
        }else if(url.contains(SourceEnum.IQIYI.getDomain())){
            pageInfo.setSource(SourceEnum.IQIYI.getSource());
        }else if(url.contains(SourceEnum.VQQ.getDomain())){
            pageInfo.setSource(SourceEnum.VQQ.getSource());
        }else if(url.contains(SourceEnum.YOUKU.getDomain())){
            pageInfo.setSource(SourceEnum.YOUKU.getSource());
        }else {
            pageInfo.setSource(SourceEnum.DOUBAN.getSource());
        }
        pageInfo.setContent(EntityUtils.toString(responseEntity));
        pageInfo.setCreateTime(new Date());
        pageInfo.setUpdateTime(new Date());
        return pageInfo;
    }

    public void processPageInfo(PageInfo pageInfo) {



    }
}
