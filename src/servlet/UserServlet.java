package servlet;

import com.alibaba.fastjson2.JSON;
import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet(name = "UserServlet",value = "/UserServlet/*")
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String uri=req.getRequestURI();
        String methodName=uri.substring(uri.lastIndexOf("/")+1);
        try {
            Method method=this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectUserByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String uid=request.getParameter("uid");
        UserDAO dao=new UserDAO();
        User user=dao.searchUserByUid(uid);
        String json= JSON.toJSONString(user);
        response.getWriter().write(json);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String uid=request.getParameter("uid");
        String password=request.getParameter("password");
        UserDAO dao=new UserDAO();
        User user=dao.loginUser(uid,password);
        String json= JSON.toJSONString(user);
        response.getWriter().write(json);
    }

    public void updateUserPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String uid=request.getParameter("uid");
        String password=request.getParameter("password");
        UserDAO dao=new UserDAO();
        boolean result=dao.updateUserPassword(uid,password);
        response.getWriter().print(result);
    }
}
