<%@include file="../header/login.jsp" %>
<%@include file="../basepath.jsp"  %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<link href="include/login/login.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=productName %>  - <%=browser_title %></title>
</head>
<body>
<div id="login-body" style="width: 100%;margin-top:32px">
    <div class="header-container">
        <div class="clearfix" id="login-header">
            <div class="logo">
                <a class="yun-logo" href="<%=home_page %>" target="_blank"
                   title="<%=productName %>  -<%=browser_title %>"><%=productName %>  -<%=browser_title %></a>
                <a class="pan-logo"  href="<%=home_page %>" target="_blank"
                   title="<%=productName %>  -<%=browser_title %>"><%=productName %>  -<%=browser_title %></a>
            </div>

        </div>
        <div id="login-middle" style="height:420px">
            <div class="img-content">
                <a hidefocus="true" > <img
                        class="index-slide-img" src="resources/images/error/404.jpg"
                        style="opacity: 0.1; ">
                </a> <a hidefocus="true" > <img
                    class="index-slide-img" src="resources/images/error/404.jpg"
                    style="opacity: 0.3;">
            </a> <a hidefocus="true" > <img
                    class="index-slide-img current"
                    src="resources/images/error/404.jpg"
                    style="opacity: 1;">
            </a> <a hidefocus="true" > <img
                    class="index-slide-img" src="resources/images/error/404.jpg"
                    style="opacity: 0.1; ">
            </a>
            </div>

        </div>
        <div id="login-download"   style="width: 576px; display:">
            <ul class="tab-error tab-download  clearfix" id="tab-download">
                <li>
                    <input id="__submit" type="button" value="用户端" onclick="javascript:window.location.href='/<%=productId %>'"
                           class="pass-button pass-button-submit" >
                </li>
                <li><input id="__submit" type="button" value="管理端"onclick="javascript:window.location.href='/<%=productId %>/admin'"
                           class="pass-button pass-button-submit" >
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
