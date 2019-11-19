package pers.lcf.rents.adminbase.model;

import lombok.Data;
import pers.lcf.rents.forum.model.PostDetails;
import pers.lcf.rents.utils.pageDTO;

import java.util.List;

/**
 * @ClassName PostDetailsDTO
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/19 11:19
 **/
@Data
public class PostDetailsDTO  extends pageDTO {
  private List<PostsInfoDetails> postsInfoDetailses;
}
