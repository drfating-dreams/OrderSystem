package servlet;

import factory.DAOFactory;
import vo.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/18.
 */

public class LoginServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "login.jsp";
        //1.接收传递的参数
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        //2.将请求的内容复制给VO类
        Person person = new Person();
        person.setId(id);
        person.setPassword(password);

        try{
            //进行数据库验证
            if(DAOFactory.getPersonDAOInstance().login(person)){
                //如果为真，则表示用户ID和密码合法
                //设置用户姓名到session范围中
                req.getSession().setAttribute("uname", person.getName());
                req.getSession().setAttribute("uid",person.getId());
                //修改跳转路径
                path="login_success.jsp";
            } else if(id!=null){
                //登录失败
                //设置错误信息
                req.setAttribute("err", "错误的用户ID及密码");
            }else{}
        }catch(Exception e){}
        //进行跳转
        req.getRequestDispatcher(path).forward(req,resp);
    }
}
