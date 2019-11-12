package pers.lcf.rents.adminbase.model;

import lombok.Data;

/**
 * @ClassName Report
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/12 17:58
 **/
@Data
public class Report {
    private String id;
    private  String releaseId;
    private String postsInfoId;
    private String releaseName;
    private String reportName;
    private String reportTitle;
    private String reportContents;
    private String reportImg1;
    private String reportImg2;
    private String gmtModified;
    private Boolean reportSum;
    private Boolean hasHandle;
    private  String gmtCreateBegin;
    private String gmtCreateEnd;
}
