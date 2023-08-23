package servlet;

import com.alibaba.fastjson2.JSON;
import dao.EmployeeDAO;
import model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@WebServlet(name = "EmployeeServlet", value = "/EmployeeServlet/*")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeDAO dao = new EmployeeDAO();
        ArrayList<Employee> list = dao.findAll();
        String json = JSON.toJSONString(list);
        response.getWriter().write(json);
    }

    public void selectEmployeeByLimit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeDAO dao = new EmployeeDAO();
        String eno=request.getParameter("eno");
        Integer page=Integer.parseInt(request.getParameter("page"));
        Integer count=Integer.parseInt(request.getParameter("count"));
        ArrayList<Employee> list = dao.selectEmployeeByLimit(eno,page,count);
        String json = JSON.toJSONString(list);
        response.getWriter().write(json);
    }

    public void getSelectEmployeeCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeDAO dao = new EmployeeDAO();
        String eno=request.getParameter("eno");
        int count=dao.getSelectEmployeeCount(eno);
        response.getWriter().print(count);
    }

    public void addEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = request.getParameter("employee");
        Employee employee = JSON.parseObject(json, Employee.class);
        EmployeeDAO dao = new EmployeeDAO();
        boolean result = dao.addEmployee(employee);
        response.getWriter().print(result);
    }

    public void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = request.getParameter("employee");
        Employee employee = JSON.parseObject(json, Employee.class);
        EmployeeDAO dao = new EmployeeDAO();
        boolean result = dao.updateEmployee(employee);
        response.getWriter().print(result);
    }

    public void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eno = request.getParameter("eno");
        EmployeeDAO dao = new EmployeeDAO();
        boolean result = dao.deleteEmployee(eno);
        response.getWriter().print(result);
    }
}
