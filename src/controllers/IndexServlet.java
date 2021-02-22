package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page = 1;  // ページの初期値は1
        try {
            page = Integer.parseInt(request.getParameter("page"));  // 1ページから2,3と読み込んでいく時にリクエストパラメータからページ数を取得する
        } catch (NumberFormatException e) {}

        // 最大件数と開始位置を指定して全タスクを取得
        List<Task> tasks = em.createNamedQuery("getAllTasks", Task.class)
                .setFirstResult(10 * (page - 1))  // 開始位置を先頭から設定
                .setMaxResults(10)  // 1ページのmaxは10で設定
                .getResultList();  // tasksデータベースに登録されているデータをリスト形式で取得

        // 登録されているタスクの件数を取得
        long tasks_count = em.createNamedQuery("getTasksCount", Long.class).getSingleResult();

        em.close();

        request.setAttribute("tasks", tasks);
        request.setAttribute("tasks_count", tasks_count);
        request.setAttribute("page", page);

        if (request.getSession().getAttribute("_flush") != null) {
            request.setAttribute("_flush", request.getSession().getAttribute("_flush"));
            request.getSession().removeAttribute("_flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/tasks/index.jsp");
        rd.forward(request, response);
    }

}
