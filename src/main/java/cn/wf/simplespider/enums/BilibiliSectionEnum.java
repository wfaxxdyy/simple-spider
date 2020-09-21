package cn.wf.simplespider.enums;

import lombok.Getter;

/**
 * @Author: fan.wang
 * @Date: 2020/9/21 16:05
 * @description: B站分区
 */
@Getter
public enum BilibiliSectionEnum {

    TOTAL(0, "总榜"),
    ANIME(1, "动漫"),
    CHINA(168, "国创相关"),
    MUSIC(3, "音乐"),
    DANCE(129, "跳舞"),
    GAME(4, "游戏"),
    KNOWLEDGE(36, "知识"),
    DIGITAL(188, "数码"),
    LIVE(160, "生活"),
    FOOD(211, "美食"),
    MAD(119, "鬼畜"),
    SHOW(155, "时尚"),
    FUN(5, "娱乐"),
    MOVIE(181, "影视");

    private int code;
    private String msg;

    BilibiliSectionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
