<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<label for="content">内容</label><br />
<input type="text" name="content" value="${task.content}" />
<br /><br />

<button type="submit">作成</button>
