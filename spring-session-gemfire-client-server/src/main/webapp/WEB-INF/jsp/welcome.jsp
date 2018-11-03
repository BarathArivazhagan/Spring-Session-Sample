<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
   <%@ page import="java.util.concurrent.TimeUnit" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="org.springframework.session.web.http.HttpSessionManager" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="org.springframework.session.ExpiringSession" %>
<%@ page import="java.util.Enumeration" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Page</title>
<script type="text/javascript" src="./lib/jquery-1.12.4.js"> </script>
<script type="text/javascript" src="./lib/bootstrap-3.3.6-dist/js/bootstrap.min.js"> </script>
<script type="text/javascript" src="./lib/handlebars-v4.0.5.js"> </script>
<link rel="stylesheet" type="text/css" href="./lib/bootstrap-3.3.6-dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/login.css">
<script type="text/javascript">

$(document).ready(function(){
	
	
	$("#newsession").on("click",function(){	
		window.open(url);
	});
	
	 $("#testService").on("click",function(){
		
		
		$.ajax({
			
			url: "../../app/service",
			method: "GET",
			beforeSend: function(jqXHR){
				
				jqXHR.setRequestHeader("x-auth-token",sessionId)
			},
			success: function(data,status){
				if(data !=null && data.STATUS == "SUCCESS"){
					console.log(data);
									
					$("#serviceResponse").html(data.MESSAGE);
					
				}else if(data !=null && data.STATUS == "FAIL"){
					$("body").html(data.MESSAGE);
				}
			},
			error: function(jqXhr,errorMss){
				console.log("error in test service");
			}
			
		});
		
		
	}); 
	 
	 $("#testapp2").on("click",function(){
			
			
			$.ajax({
				
				url: "../../app/testapp2",
				method: "GET",
				beforeSend: function(jqXHR){
					console.log("Checking the session "+sessionId);
					jqXHR.setRequestHeader("x-auth-token",sessionId)
				},
				success: function(data,status){
					if(data !=null && data.STATUS == "SUCCESS"){
						console.log(data);
										
						$("#serviceResponse").html(data.MESSAGE);
						
					}else if(data !=null && data.STATUS == "FAIL"){
						$("body").html(data.MESSAGE);
					}
				},
				error: function(jqXhr,errorMss){
					console.log("error in test service");
				}
				
			});
			
			
		}); 
	 $("#viewUsers").on("click",function(){
			
			
			$.ajax({
				
				url: "../../app/users",
				method: "GET",
				beforeSend: function(jqXHR){
					console.log("Checking the session "+sessionId);
					jqXHR.setRequestHeader("x-auth-token",sessionId)
				},
				success: function(data,status){
					if(data !=null ){
						console.log(data);
						var source   = document.getElementById("users-template").innerHTML;
						var template = Handlebars.compile(source);	
						var html = template(data);
						console.log(html);
						$("#users").html(html);
						
					}
				},
				error: function(jqXhr,errorMss){
					console.log("error in test service");
				}
				
			});
			
			
		}); 
	 
	 
	 

	
});



</script>
</head>
<body>
<%
HttpSessionManager sessionManager =
            (HttpSessionManager) request.getAttribute(HttpSessionManager.class.getName());
     
 
 

String contextPath = request.getContextPath();


String sessionId=request.getSession().getId();
Date creationDate=new Date(request.getSession().getCreationTime());

Date lastAccessedDate=new Date(request.getSession().getLastAccessedTime());
String userName=(String)request.getSession().getAttribute("USER_NAME");
boolean isExpired=System.currentTimeMillis()-TimeUnit.SECONDS.toMillis(request.getSession().getMaxInactiveInterval()) >= request.getSession().getLastAccessedTime();
String sessionToken=response.getHeader("x-auth-token"); 

/* Map<String, String> map = new HashMap<String, String>();
Enumeration headerNames = request.getHeaderNames();
while (headerNames.hasMoreElements()) {
	String key = (String) headerNames.nextElement();
	String value = request.getHeader(key);
	
	map.put(key, value);
}
for(Map.Entry<String, String> entry:map.entrySet()){
	System.out.println("HEADER KEY ==> "+entry.getKey()+ "   HEADER VALUE IS "+entry.getValue());
}
String sessionToken=map.get("x-auth-token"); */
%>
<script>

var url="http://localhost:8083/app/newSession";
var sessionId="<%=sessionId%>";


</script>

<div class="container">
  
	<div class="row rowTitle">
	  <div class="title">Welcome to Test Spring Session Strategy</div>
	</div>
	<div class="row rowTable">
	  <div class="col-sm-2 col-md-2"></div>
	  <div class="col-sm-8 col-md-8">
	  <table class="table-bordered" id="sessiontable">
	    <tbody>
	      <tr>
	        <td>User Name</td>
	        <td id="userName"> <%= userName%></td>      
	      </tr>
	      <tr>
	        <td>Session ID</td>
	        <td id="sessionId"> <%= sessionId %></td>
	        
	      </tr>
	      <tr>
	        <td>Session Creation Date</td>
	        <td id="creationDate"><%= creationDate %></td>
	       
	      </tr>
	      <tr>
	        <td>Session Last Accessed Date </td>
	        <td><%= lastAccessedDate %></td>
	       
	      </tr>
	      <tr>
	        <td>Is Session Expired </td>
	        <td><%=isExpired %></td>
	       
	      </tr>
	      <tr>
	        <td>Click here to open new session</td>
	        <td><button id="newsession" class="btn btn-primary"> NEW SESSION</button></td>
	       
	      </tr>
	        <tr>
	        <td>Test Service </td>
	        <td><button id="testService" class="btn btn-primary">TEST SERVICE</button></td>
	       
	      </tr>
	      <tr>
	        <td>Test App2 </td>
	        <td><button id="testapp2" class="btn btn-primary testApp2">TEST app2</button></td>
	       
	      </tr>
	        <tr>
	        <td> Log out </td>
	        <td><a href="/app/logout">LOG OUT</a></td>
	       
	      </tr>
	       <tr>
	        <td>Result </td>
	        <td><div id="serviceResponse" class="response"></div></td>
	       
	      </tr>
	    </tbody>
	  </table>
	  </div>
	  <div class="col-sm-2 col-md-2"></div>
	</div>
	<div class="row row-users">
		  <button id="viewUsers" class="btn btn-lg btn-primary btn-block" >View Users</button>  
		  <div id="users"> 
		  </div>
	</div>
</div>

</body>
<script id="users-template" type="text/x-handlebars-template">
 <h2>Users</h2>      
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>User ID</th>
        <th>User Name</th>
        <th>Age</th>
		<th>Gender</th>
		<th>Email</th>
      </tr>
    </thead>
    <tbody
	{{#each this}}
      <tr>
        <td>{{this.userId}}</td>
        <td>{{this.userName}}</td>
        <td>{{this.userAge}}</td>
 		<td>{{this.userGender}}</td>
 		<td>{{this.userEmailId}}</td>
      </tr>  
	{{/each}}   
    </tbody>
  </table>
</script>
</html>
