package pers.lcf.rents.adminbase.model;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName OrdinaryUsersPei
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/9 19:15
 **/
@Data
public class OrdinaryUsersPei {
    private Integer man;
    private Integer woman;
    private Integer yesreal;
    private Integer noreal;
    private Integer normal;
    private Integer unnormal;
    private Map<String, Integer> sex;
    private Map<String, Integer> isState;
    private Map<String, Integer> hasRealName;
}
