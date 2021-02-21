package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import models.validators.TaskValidator;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Task task = em.find(Task.class, (Integer)request.getSession().getAttribute("task_id"));  // 変更するIDを取得

            String content = request.getParameter("content");
            task.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            task.setUpdated_at(currentTime);

            // バリデーションを実行
            List<String> errors = TaskValidator.validate(task);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("task", task);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/tasks/edit.jsp");
                rd.forward(request, response);
            } else {

                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                // 不要になったセッションスコープを削除
                request.getSession().removeAttribute("task_id");

                request.getSession().setAttribute("_flush", "タスクを更新しました!");

                response.sendRedirect(request.getContextPath() + "/index");
            }

        }
    }

}
