package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CSRF対策
        String _token = request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();
            em.getTransaction().begin();

            Task task = new Task();

            String content = request.getParameter("content");
            task.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            task.setCreated_at(currentTime);
            task.setUpdated_at(currentTime);

            em.persist(task);
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("_flush", "新規タスクの登録が完了しました!");

            response.sendRedirect(request.getContextPath() + "/index");
        }
    }

}