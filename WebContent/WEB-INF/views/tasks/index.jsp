<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../templates/app.jsp">
    <c:param name="content">
        <h2>タスク一覧</h2>

        <ul>
            <c:forEach var="task" items="${tasks}">
                <li>
                    <a href="${pageContext.request.contextPath}/show?${task.id}"><c:out value="${task.id}" /></a>
                    &ensp; &gt; &ensp;
                    <c:out value="${task.content}" />
                </li>
            </c:forEach>
        </ul>

        <p><a href="${pageContext.request.contextPath}/new">新規作成</a></p>
    </c:param>
</c:import>