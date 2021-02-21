<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../templates/app.jsp">
    <c:param name="content">

        <c:choose>
            <c:when test="${task == null}">
                <h2>お探しのタスクは見つかりませんでした。</h2>
            </c:when>

            <c:otherwise>
                <h2>id : ${task.id}の詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th><c:out value="タスク内容" /></th>
                            <td><c:out value="${task.content}" /></td>
                        </tr>
                        <tr>
                            <th><c:out value="作成日時" /></th>
                            <td><fmt:formatDate value="${task.created_at}" pattern="yyyy/MM/dd HH:mm" /></td>
                        </tr>
                        <tr>
                            <th><c:out value="更新日時" /></th>
                            <td><fmt:formatDate value="${task.updated_at}" pattern="yyyy-MM-dd HH:mm" /></td>
                        </tr>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <p><a href="${pageContext.request.contextPath}/index">一覧に戻る</a></p>
        <p><a href="${pageContext.request.contextPath}/edit?id=${task.id}">このページを編集する</a></p>

    </c:param>
</c:import>