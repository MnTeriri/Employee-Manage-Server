package servlet;

import com.alibaba.fastjson2.JSON;
import dao.EmployeeDAO;
import dao.SalaryDAO;
import model.Salary;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@WebServlet(name = "SalaryServlet", value = "/SalaryServlet/*")
public class SalaryServlet extends HttpServlet {
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
        SalaryDAO dao = new SalaryDAO();
        ArrayList<Salary> list = dao.findAll();
        String json = JSON.toJSONString(list);
        response.getWriter().write(json);
    }

    public void selectSalary(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SalaryDAO dao = new SalaryDAO();
        String eno=request.getParameter("eno");
        ArrayList<Salary> list = dao.selectSalary(eno);
        String json = JSON.toJSONString(list);
        response.getWriter().write(json);
    }

    public void selectSalaryByLimit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SalaryDAO dao = new SalaryDAO();
        String eno=request.getParameter("eno");
        Integer page=Integer.parseInt(request.getParameter("page"));
        Integer count=Integer.parseInt(request.getParameter("count"));
        ArrayList<Salary> list = dao.selectSalaryByLimit(eno,page,count);
        String json = JSON.toJSONString(list);
        response.getWriter().write(json);
    }

    public void getSelectSalaryCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SalaryDAO dao = new SalaryDAO();
        String eno=request.getParameter("eno");
        int count=dao.getSelectSalaryCount(eno);
        response.getWriter().print(count);
    }

    public void addSalary(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = request.getParameter("salary");
        Salary salary = JSON.parseObject(json, Salary.class);
        EmployeeDAO employeeDAO=new EmployeeDAO();
        if(employeeDAO.employeeExist(salary.getEno())==0){
            response.getWriter().print(false);
            return;
        }
        SalaryDAO dao = new SalaryDAO();
        boolean result = dao.addSalary(salary);
        response.getWriter().print(result);
    }

    public void updateSalary(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = request.getParameter("salary");
        Salary salary = JSON.parseObject(json, Salary.class);
        SalaryDAO dao = new SalaryDAO();
        boolean result = dao.updateSalary(salary);
        response.getWriter().print(result);
    }
}
