package cn.wf.simplespider.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;



import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 视频扩展表
 * </p>
 *
 * @author wang fan
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ss_video_extend")
public class VideoExtend extends Model<VideoExtend> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * bvid
     */
    private String bvid;

    /**
     * 视频id
     */
    @TableField("video_id")
    private Long videoId;

    /**
     * 点赞
     */
    private Long like;

    /**
     * 硬币
     */
    private Long coin;

    /**
     * 收藏
     */
    private Long favorite;

    /**
     * 转发
     */
    private Long share;

    /**
     * 弹幕
     */
    private Long danmaku;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
