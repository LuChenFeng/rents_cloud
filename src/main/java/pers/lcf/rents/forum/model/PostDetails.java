package pers.lcf.rents.forum.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName PostDetails
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/6 15:28
 * 帖子详细信息展示对象
 **/
@Data
public class PostDetails {
    private String userId;
    private String postsId;
    private String postTypeName;
    private String userName;
    private String avatar;
    private String sex;
    private String birthady;
    private PostsInfo postsInfo;
    private List<String> imgs;
    private Long pageTotal;
}
