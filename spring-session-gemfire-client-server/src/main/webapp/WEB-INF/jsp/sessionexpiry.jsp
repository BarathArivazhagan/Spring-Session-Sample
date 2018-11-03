<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Session Expired</title>
<script type="text/javascript" src="./lib/jquery-1.12.4.js"> </script>
<script type="text/javascript" src="./lib/bootstrap-3.3.6-dist/js/bootstrap.min.js"> </script>
<link rel="stylesheet" type="text/css" href="./lib/bootstrap-3.3.6-dist/css/bootstrap.min.css">
</head>
<script>
 $(document).ready(function(){
	 $("#login").on("click",function(){	
			window.open("../../APP");
		});
 })

</script>
<body>


<div>OOPS ! Session Expired . Login again <button id="login">Login</button></div>

</body>
</html>