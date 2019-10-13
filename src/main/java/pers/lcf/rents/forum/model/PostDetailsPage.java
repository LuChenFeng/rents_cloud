package pers.lcf.rents.forum.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName PostDetailsPage
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/13 15:36
 **/
@Data
public class PostDetailsPage {
    private List<PostDetails> PostDetails;
   private Long pageTotal;
}
