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
 * Servlet implementation class NewServlet
 */
@WebServlet("/new")
public class NewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();  // DBの接続を開始

        Task task = new Task();  // Taskインスタンスを生成（tasksデータベースにデータを登録するため）

        String content = "DBとアプリケーションの接続がうまくできているかの確認";
        task.setContent(content);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        task.setCreated_at(currentTime);
        task.setUpdated_at(currentTime);

        em.persist(task);
        em.getTransaction().commit();

        response.getWriter().append(Integer.valueOf(task.getId()).toString());

        em.close();
    }

}
