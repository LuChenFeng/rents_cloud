package pers.lcf.rents.forum.model;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

import java.util.List;

/**
 * @ClassName CurrentComment
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/6 20:20
 **/
@Data
public class CurrentComment {
    private String userId;
    private String commentId;
    private String userName;
    private String avatar;
    private String content;
    private String gmtCreate;
    private List postsReplies= CollUtil.newArrayList();
    private Integer repliesSize;
}
