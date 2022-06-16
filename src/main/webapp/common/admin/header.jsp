<%@page import="com.perfumeworld.dto.SessionUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div id="navbar" class="navbar navbar-default          ace-save-state" style="background-color: #000000">
    <div class="navbar-container ace-save-state" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="#" class="navbar-brand">
                <small>
                    <i class="fa fa-user"></i>
                    Trang quản trị
                </small>
            </a>
        </div>
        <div class="navbar-buttons navbar-header pull-right collapse navbar-collapse" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue dropdown-modal">

                        <%if(session.getAttribute("sessionUser") != null){%>
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle" style="background-color: #000000">
                        Xin chào, <%=((SessionUser) request.getSession().getAttribute("sessionUser")).getFullName()%>
                    </a>
                <li class="light-blue dropdown-modal" >
                    <a href='<c:url value='/logout'/>' style="background-color: #000000">
                        <i class="ace-icon fa fa-power-off"></i>
                        Thoát
                    </a>
                </li>
                <%} else {%>
                <a data-toggle="dropdown" href="<c:url value='/login'/>" class="dropdown-toggle" style="background-color: #000000">
                    Đăng nhập
                </a>
                <li class="light-blue dropdown-modal">
                    <a href='<c:url value='/home'/>' style="background-color: #000000">
                        <i class="ace-icon fa fa-power-off"></i>
                        Thoát
                    </a>
                </li>
                <%}%>
                </li>
            </ul>
        </div>
    </div>
</div>