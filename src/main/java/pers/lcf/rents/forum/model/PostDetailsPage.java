package pers.lcf.rents.forum.model;

import lombok.Data;
import pers.lcf.rents.utils.pageDTO;

import java.util.List;

/**
 * @ClassName PostDetailsPage
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/13 15:36
 **/
@Data
public class PostDetailsPage extends pageDTO {
    private List<PostDetails> PostDetails;
   private Long pageTotal;
}
