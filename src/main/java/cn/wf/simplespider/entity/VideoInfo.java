package cn.wf.simplespider.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * B站视频信息表
 * </p>
 *
 * @author wang fan
 * @since 2020-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ss_video_info")
public class VideoInfo extends Model<VideoInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 视频id
     */
    @TableField("video_id")
    private Long videoId;

    /**
     * 分区
     */
    private Integer section;

    /**
     * 排名
     */
    private Long rank;

    /**
     * 标题
     */
    private String title;

    /**
     * 链接
     */
    private String url;

    /**
     * 综合得分
     */
    private Long pts;

    /**
     * 播放量
     */
    private String play;

    /**
     * 评论
     */
    private String comments;

    /**
     * 作者
     */
    private String author;

    /**
     * 日期
     */
    private Date day;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
