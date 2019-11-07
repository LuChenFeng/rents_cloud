package pers.lcf.rents.adminbase.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.lcf.rents.adminbase.model.OrdinaryUser;
import pers.lcf.rents.adminbase.model.OrdinaryUserDTO;
import pers.lcf.rents.adminbase.service.AdminBaseService;
import pers.lcf.rents.userbase.mapper.UserInfoMapper;
import pers.lcf.rents.userbase.model.UserInfoExample;

import java.util.*;

/**
 * @ClassName AdminBaseServiceImpl
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/5 12:46
 **/
@Service
public class AdminBaseServiceImpl implements AdminBaseService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public OrdinaryUserDTO getOrdinaryUsersByDTO(OrdinaryUserDTO ordinaryUserDTO) {
        int[] startEnd = PageUtil.transToStartEnd(ordinaryUserDTO.getPageNo(), ordinaryUserDTO.getPageSize());
        OrdinaryUser user = ordinaryUserDTO.getOrdinaryUsers().get(0);

//        判断时间范围，是否有选中时间
        if (user.getGmtCreateBegin() != null && !("".equals(user.getGmtCreateBegin()))
                && user.getGmtCreateEnd() != null && !("".equals(user.getGmtCreateEnd()))) {
            Date dateBegin = DateUtil.parse(user.getGmtCreateBegin());
            Date dateEnd = DateUtil.parse(user.getGmtCreateEnd());
            if (dateBegin.compareTo(dateEnd) > 0) {
                String date;
                date = user.getGmtCreateBegin();
                user.setGmtCreateBegin(user.getGmtCreateEnd());
                user.setGmtCreateEnd(date);
            }
        } else {
            user.setGmtCreateBegin(null);
            user.setGmtCreateEnd(null);
        }

        List<List<?>> dtoList = userInfoMapper.getOrdinaryUsersByDTO(user, startEnd[0], startEnd[1]);
        List<OrdinaryUser> ordinaryUsers = (List<OrdinaryUser>) dtoList.get(0);
        OrdinaryUserDTO dto = new OrdinaryUserDTO();
        dto.setOrdinaryUsers(ordinaryUsers);
        List<Integer> count = (List<Integer>) dtoList.get(1);
        int totalCount = count.get(0);
        int totalPage = PageUtil.totalPage(totalCount, ordinaryUserDTO.getPageSize());
        dto.setTotalCount(totalCount);
        dto.setTotalPage(totalPage);
        dto.setPageNo(ordinaryUserDTO.getPageNo());
        dto.setPageSize(ordinaryUserDTO.getPageSize());
        return dto;
    }
}
