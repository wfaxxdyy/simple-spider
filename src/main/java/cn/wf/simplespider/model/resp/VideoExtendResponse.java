package cn.wf.simplespider.model.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: fan.wang
 * @Date: 2020/9/25 10:49
 * @description:
 */
@Data
public class VideoExtendResponse implements Serializable {

    private static final long serialVersionUID = -2519383686962365355L;

    /**
     * bvid
     */
    private String bvid;
    /**
     * 硬币
     */
    private Long coin;
    /**
     * copyright
     */
    private Long copyright;
    /**
     * 弹幕
     */
    private Long danmaku;
    /**
     * 收藏
     */
    private Long favorite;
    /**
     * 点赞
     */
    private Long like;
    /**
     * 评论
     */
    private Long reply;
    /**
     * 分享
     */
    private Long share;
    /**
     * 观看
     */
    private Long view;
}
