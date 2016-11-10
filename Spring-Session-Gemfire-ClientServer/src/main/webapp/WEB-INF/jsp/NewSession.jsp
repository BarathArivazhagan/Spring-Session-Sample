<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@ page import="java.util.concurrent.TimeUnit" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="org.springframework.session.web.http.HttpSessionManager" %>
<%@ page import="org.springframework.session.ExpiringSession" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Session Page</title>
<script type="text/javascript" src="./lib/jquery-1.12.4.js"> </script>
<script type="text/javascript" src="./lib/bootstrap-3.3.6-dist/js/bootstrap.min.js"> </script>
<link rel="stylesheet" type="text/css" href="./lib/bootstrap-3.3.6-dist/css/bootstrap.min.css">
<script type="text/javascript">

$(document).ready(function(){
	
	$("#newsession").on("click",function(){
		
		window.open(url);
	});
	

	
});

</script>
</head>
<body>
<%
HttpSessionManager sessionManager =
(HttpSessionManager) request.getAttribute(HttpSessionManager.class.getName());

System.out.print("Htp session Mangaer "+sessionManager);


String contextPath = request.getContextPath();


String sessionId=request.getSession().getId();
Date creationDate=new Date(request.getSession().getCreationTime());

Date lastAccessedDate=new Date(request.getSession().getLastAccessedTime());
String userName=(String)request.getSession().getAttribute("USER_NAME");
boolean isExpired=System.currentTimeMillis()-TimeUnit.SECONDS.toMillis(request.getSession().getMaxInactiveInterval()) >= request.getSession().getLastAccessedTime();

%>
<script>

var url="http://localhost:8083/APP/newSession";
</script>
<div>
	<div>
		Hello <%= userName%>
	
	</div>
	<div>
		Session ID is  <%= sessionId %>
	</div>
	<div>
		Session Creation Date  is  <%= creationDate %>
	</div>
	<div>
		Session Last Accessed Date  is  <%= lastAccessedDate %>
	</div>
	<div>
	
		Is Session Expired <%=isExpired %>
	</div>
<div>
	<span>Click here to open new session </span>
	<span><button id="newsession" value="NEW SESSION"> NEW SESSION</button></span>

</div>
<div>


<div>

<span>Click here to close this session </span>
	<span><a href="/APP/logout">LOG OUT</a></span>

</div>
<div>
		TestService  <span id="testService"> test</span>


	</div>
	<div id="serviceResponse">
	
	</div>


</div>

</body>
</html>