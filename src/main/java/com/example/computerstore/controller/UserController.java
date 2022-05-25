package com.example.computerstore.controller;

import com.example.computerstore.controller.ex.*;
import com.example.computerstore.entity.User;
import com.example.computerstore.service.IUserService;
import com.example.computerstore.service.ex.InsertException;
import com.example.computerstore.service.ex.UsernameDuplicatedException;
import com.example.computerstore.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @param
 * @return
 */
//@Controller
@RestController//效果等同于@Controller+@ResponseBody
@RequestMapping("/users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    /**
     * 约定大于配置：开发思想来完成，省略大量的配置甚至注解的编写
     *1、接受数据方式：请求处理方法的参数列表设置为pojo类型来接受前端的数据
     *SprinBoot会将前端的url地址中的参数名和pojo类的属性进行比较，如果这
     * 两个名称相同，则将值注入到pojo类中对应的属性上
     */

    //@ResponseBody 表示此方法的响应结果以json格式进行数据的响应给到前端
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
    }
//    @RequestMapping("reg")
//    public JsonResult<Void> reg(User user){
//        JsonResult<Void> result=new JsonResult<>();
//        try {
//            userService.reg(user);
//            result.setState(200);
//            result.setMessage("用户注册成功");
//        } catch (UsernameDuplicatedException e) {
//            result.setState(4000);
//            result.setMessage("用户名被占用");
//        }catch (InsertException e){
//            result.setState(5000);
//            result.setMessage("注册时产生未知的异常");
//        }
//        return result;
//    }

    /**
     *2、接收数据方式：请求处理方法的参数列表设置为非pojo类型
     * SpringBoot会直接将请求的参数名和方法的参数名直接进行比较，
     * 如果名称相同则自动完成值的依赖注入
     */
    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data=userService.login(username,password);

        //向session对象中完成数据的绑定（session全局的）
        session.setAttribute("uid",data.getUid());
        session.setAttribute("Username",data.getUsername());
        //获取session中绑定的数据
//        System.out.println(getuidFromSession(session));
//        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(OK,data);
    }

    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(String newPassword, String oldPassword, HttpSession session){
        Integer uid=getuidFromSession(session);
        String username=getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User data=userService.getByUid(getuidFromSession(session));
        return new JsonResult<User>(OK,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user对象有四部分的数据：username,phone,email,gender
        //uid数据需要再次封装到user对象中
        Integer uid=getuidFromSession(session);
        String username=getUsernameFromSession(session);
        userService.changeInfo(uid,user,username);
        return new JsonResult<>(OK);
    }

    //设置上传文件的最大值
    public static final int AVATAR_MAX_SIZE=10*1024*1024;
    //上传文件的类型
    public static final List<String> AVATAR_TYPE=new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmg");
        AVATAR_TYPE.add("image/gif");
    }
    /**
     * MultipartFile接口是SpringMVC提供的一个接口，这个接口为我们包装了
     * 获取文件类型的数据（任何类型的file都可以接收），SpringBoot它整合了
     * SpringMVC，只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile
     * 的参数，然后SpringBoot自动将传递给服务器的文件数据赋值给这个参数
     *
     * @RequestParam 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数上
     * 如果名称不一致则可以使用@RequestParam注释进行标记和映射。类似持久层mybatis@Param
     *
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, MultipartFile file){
        //判断文件是否为空
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if(file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超出限制");
        }
        //判断文件的类型是否是我们规定的后后缀类型
        String contentType=file.getContentType();//获取文件的类型
        if(!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        /**视频老师的方法：传到tomcat容器，每次运行都生成新的容器了，不好用
         * //上传的文件.../upload/文件.png
         *         String parent=session.getServletContext().getRealPath("upload");
         *         //File对象指向这个路径
         *         File dir=new File(parent);
         *         if(!dir.exists()){//检测目录是否存在
         *             dir.mkdirs(); //创建当前目录
         *         }
         */

        /**
         * 修改传到本地文件路径
         */
        File dir = new File("D:\\Preject\\IDEA\\ComputerStore/src/main/resources/static/upload");

        //获取到这个文件的名称，UUID根据来生成一个新的字符串作为文件名
        String originalFilename= file.getOriginalFilename();
        int index=originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);
        String filename= UUID.randomUUID().toString().toUpperCase()+suffix;

        File dest=new File(dir,filename);//是一个空文件
        //参数file中的数据写入到空文件中
        try {
            file.transferTo(dest);//将file文件中的数据写入到dest文件中
        }catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUpdateIOException("文件读写异常");
        }

        Integer uid = getuidFromSession(session);
        String username=getUsernameFromSession(session);
        //返回头像的路径/upload/**.png
        String avatar="../upload/"+filename;
        System.out.println(avatar);
        userService.changeAvatar(uid,avatar,username);
        //返回用户头像的路径给前端页面，将来用于头像展示使用
        JsonResult<String> data=new JsonResult<>(OK,avatar);
        System.out.println(data.getData());
        return data;
    }


}
