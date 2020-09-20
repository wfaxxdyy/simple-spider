package cn.wf.simplespider.enums;

import lombok.Getter;

/**
 * @Author: fan.wang
 * @Date: 2020/9/18 17:26
 * @description: 数据来源
 */
@Getter
public enum SourceEnum {

    BILIBILI(1, "bilibili", "B站"),
    YOUKU(2, "youku", "优酷"),
    IQIYI(3, "iqiyi", "爱奇艺"),
    VQQ(4, "v.qq", "腾讯视频"),
    DOUBAN(5, "douban", "豆瓣");

    private Integer type;
    private String domain;
    private String source;

    SourceEnum(Integer type, String domain, String source) {
        this.type = type;
        this.domain = domain;
        this.source = source;
    }
}
