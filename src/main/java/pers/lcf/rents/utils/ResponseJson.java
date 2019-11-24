package pers.lcf.rents.utils;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Param:
 * @Return:
 * @Author: lcf
 * @Date: 2019/10/3 21:15
 * 统一响应体
 */
@Component
@Data
public class ResponseJson<T> implements Serializable {
    private static final long serialVersionUID = -3820536327275382207L;
    private int code;
    private T data;
    private String msg;

    /**
     * @Param: [data]
     * @Return: void
     * @Author: lcf
     * @Date: 2019/10/3 21:14
     * 成功执行时调用
     */
    public void setSuccessResPonse(T data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }

    /**
     * @Param: [data]
     * @Return: void
     * @Author: lcf
     * @Date: 2019/10/3 21:16
     * 执行失败时调用
     */
    public void setErrorResPonse(T data,String msg) {
        this.code = 401;
        this.msg =msg;
        this.data = data;
    }

    public void setResPonse(String flag) {
        if ("".equals(flag) || flag == null) {
            this.code = 500;
            this.msg = "error";
        } else {
            this.code = 200;
            this.msg = "success";
        }
        this.data = null;
    }

    public void setResPonse(int flag) {
        if (flag <= 0) {
            this.code = 500;
            this.msg = "error";
        } else {
            this.code = 200;
            this.msg = "success";
        }
        this.data = null;
    }

    public  void setResPonseSelfMsg(String msg){
        this.code = 450;
        this.msg = msg;
        this.data=null;
    }

}
