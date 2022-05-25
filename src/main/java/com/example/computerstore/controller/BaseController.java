package com.example.computerstore.controller;

import com.example.computerstore.controller.ex.*;
import com.example.computerstore.service.ex.*;
import com.example.computerstore.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 控制层类的基类
 */
public class BaseController {
    //操作成功的状态码
    public static final int OK=200;

    //请求处理方法。这个方法的返回值就是需要传递给前端的数据
    //自动将异常对象传递给此方法的参数列表上
    //当前项目中产生了异常。被统一拦截到此方法中，这个方法此时就充当的是请求处理方法，方法的返回值直接给到前端
    @ExceptionHandler({ServiceException.class,FileUpdateException.class}) //用于统一处理抛出异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result=new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名被占用");
        }else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("插入时产生未知的异常");
        }else if(e instanceof UserNotFoundException){
            result.setState(5001);
            result.setMessage("用户数据不存在异常");
        } else if(e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("用户名的密码错误异常");
        }else if(e instanceof UpdateException){
            result.setState(5003);
            result.setMessage("更新数据时产生未知异常");
        }else if(e instanceof AddressCountLimitException){
            result.setState(5004);
            result.setMessage("用户的地址数量达到上限");
        }else if(e instanceof AccessDeniedException){
            result.setState(5005);
            result.setMessage("权限异常");
        }else if(e instanceof AddressNotFoundException){
            result.setState(5006);
            result.setMessage("未找到用户地址");
        }else if(e instanceof DeleteException){
            result.setState(5007);
            result.setMessage("删除时产生未知异常");
        }else if(e instanceof FileEmptyException){
            result.setState(6000);
            result.setMessage("文件为空异常");
        } else if(e instanceof FileSizeException){
            result.setState(6001);
            result.setMessage("文件大小异常");
        }else if(e instanceof FileTypeException){
            result.setState(6002);
            result.setMessage("文件类型异常");
        }else if(e instanceof FileStateException){
            result.setState(6003);
            result.setMessage("文件状态异常");
        }else if(e instanceof FileUpdateIOException){
            result.setState(6004);
            result.setMessage("IO流异常");
        }
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录的用户uid的值
     */
    protected final Integer getuidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前session对象中的Username
     * @param session session对象
     * @return 当前登录的用户用户名
     *
     *HttpSession在实现类中重写父类中的toString(),不是句柄信息的输出
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("Username").toString();
    }
}
