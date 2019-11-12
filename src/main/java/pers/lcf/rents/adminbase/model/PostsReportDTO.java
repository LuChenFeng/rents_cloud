package pers.lcf.rents.adminbase.model;

import lombok.Data;
import pers.lcf.rents.utils.pageDTO;

import java.util.List;

/**
 * @ClassName PostsReportDTO
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/12 19:46
 **/
@Data
public class PostsReportDTO extends pageDTO {
    private List<Report> reports;
}
