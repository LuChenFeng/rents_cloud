package pers.lcf.rents.forum.model;

import lombok.Data;

@Data
public class PostsType {
    private String id;
    private Byte isHave;
    private String postTypeName;
    private String remarks;
    private String gmtCreate;
    private String gmtModified;
}