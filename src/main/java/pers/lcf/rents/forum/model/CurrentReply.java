package pers.lcf.rents.forum.model;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

import java.util.List;

/**
 * @ClassName CurrentReply
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/7 16:41
 **/
@Data
public class CurrentReply {
    private String userId;
    private String replyId;
    private String userName;
    private String avatar;
    private String content;
    private String gmtCreate;
}
