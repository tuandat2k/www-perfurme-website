<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<spring:eval expression="@property.getProperty('user.login')" var="loginUrl"/>
<spring:eval expression="@property.getProperty('product.discount')" var="discountPro"/>
<spring:eval expression="@property.getProperty('cart.url')" var="cartUrl"/>
<spring:eval expression="@property.getProperty('product.web.find')" var="findProduct"/>
<spring:eval expression="@property.getProperty('home')" var="home"/>
<spring:eval expression="@property.getProperty('user.register')" var="userRegis"/>
<spring:eval expression="@property.getProperty('category.products')" var="url"/>
<spring:eval expression="@property.getProperty('page.page.product')" var="pageProp"/>
<spring:eval expression="@property.getProperty('page.maxPageItem.product')" var="maxPageItemProp"/>
<spring:eval expression="@property.getProperty('cart.url')" var="cartUrl"/>
<c:url var="productofcate" value="${url}"/>
<c:url var="find_product" value="${findProduct}"></c:url>
<c:url var="homeURl" value="${home}"/>
<c:url value="${discountPro}" var="discount_url"></c:url>
<c:url value="${userRegis}" var="user_regis"></c:url>
<!DOCTYPE html>
<div class="subscribe">
    <div class="container">
        <form action="${find_product}" method="POST">
            <input type="text" placeholder="Nhập sản phẩm cần tìm" id="name" name="name" class="text">
            <input type="submit"   value="Tìm Kiếm">
        </form>
    </div>
</div>
<div class="footer">
    <div class="container">
        <div class="footer-grids">
            <div class="col-md-3 about-us">
                <h3>Về chúng tôi</h3>
                <p>Ra đời vào ngày 14/3/2022 và hiện tại là nơi cung cấp hơn 200 nhãn hiệu nước hoa cao cấp trên thế giới. Là website nước hoa đầu tiên tại Việt Nam. Định hướng của chúng tôi là trở thành nhà cung cấp nước hoa số 1 tại VN, mang lại sự lựa chọn đa dạng, sự thuận tiện, tiết kiệm tiền & thời gian, đồng thời mang đến sự yên tâm & hài lòng đến với khách hàng.</p>
            </div>
            <div class="col-md-3 ftr-grid">
                <h3>Thông tin</h3>
                <ul class="nav-bottom">
                    <li><a href="${homeURl}">Trang chủ</a></li>
                    <li><a href="${discount_url}?page=${pageProp}&&maxPageItem=${maxPageItemProp}">Khuyến Mãi</a></li>
                    <li><a href="<c:url value="${cartUrl}"/>">Giỏ Hàng</a></li>
                </ul>
            </div>
            <div class="col-md-3 ftr-grid">
                <h3>Tài khoản</h3>
                <ul class="nav-bottom">
                    <li><a href="http://localhost:8080/perfumeWorld/login">Đăng nhập</a></li>
                    <li><a href="http://localhost:8080/perfumeWorld/register">Đăng ký</a></li>
                </ul>
            </div>
            <div class="col-md-3 ftr-grid">
                <h3>Danh Mục</h3>
                <ul class="nav-bottom">
                    <c:forEach items="${menuCate}" var="i">
                        <li>
                            <a href="${productofcate}${i.id}?page=${pageProp}&&maxPageItem=${maxPageItemProp}">${i.name}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="copywrite">
    <div class="container">
        <div class="copy">
        	<p style="padding-right: 15px;padding-left: 15px;"> 		        OFFICE 247 TSP CO.,LTD; 228 Lý Chính Thắng, P9, Q.3, TP HCM
		Mã số thuế: 0312942912  do Sở Kế hoạch đầu tư TP HCM cấp ngày 24/09/2014</p>
        </div>
        <div class="social">
            <a href="#"><i class="facebook"></i></a>
            <a href="#"><i class="twitter"></i></a>
            <a href="#"><i class="dribble"></i></a>
            <a href="#"><i class="google"></i></a>
            <a href="#"><i class="youtube"></i></a>
        </div>
        <div class="clearfix"></div>
    </div>
</div>