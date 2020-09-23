新增表 :
    CREATE TABLE `ss_video_info` (
      `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
      `video_id` bigint(20) NOT NULL COMMENT '视频id',
      `section` int(4) NOT NULL COMMENT '分区',
      `rank` bigint(10) NOT NULL COMMENT '排名',
      `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
      `url` varchar(255) NOT NULL COMMENT '链接',
      `pts` bigint(12) NOT NULL COMMENT '综合得分',
      `play` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '播放量',
      `comments` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论',
      `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者',
      `day` datetime NOT NULL COMMENT '日期',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='B站视频信息表';