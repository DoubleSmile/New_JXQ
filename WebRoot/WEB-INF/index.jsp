<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <script src="/New_JXQ/js/jquery-2.1.3.js"></script>
  <script type="text/javascript">
    $(document).ready(
    		function(){
   			 var json={"phone":"18829211793","password":"lsdf"};
   	       $.ajax({
   			type: "post",
   			contentType:"application/json",
   			url: "/New_JXQ/user/login.do",
   			data: JSON.stringify(json),
   			success: function(data){
   				
   		    	$("p").html(data);
   			}
   		});
         
    });
    
  </script>
  
  <body>
    能不能显示的漂亮点<br>
    
     <h3><p class="demo"></p></h3>
    
    
    
      
   <!--       
    
    <form name="testForm" action="/New_JXQ/image/qiniuUpload.do" enctype="multipart/form-data" method="post"">  
        <div id="upload">  
            <input type="file" name="file">  
        </div>  
         
        <input type="submit" value="上传" >  
               
    </form> 
    
         ${token}
    <form method="post" action="http://upload.qiniu.com/"
	                 enctype="multipart/form-data">
	  <input name="key" type="hidden" value="Test10.jpg">
	  <input name="token" type="hidden" value="${token}">
	  <input name="file" type="file" />
	  <input name="x:location" value="door" type="hidden">
	  <input name="x:name" value="Test10.jpg" type="hidden">
      <input name="x:schoolID" value="12" type="hidden">  
	  <input name="accept" type="hidden" />
	  <input type="submit" value="上传" > 
     </form>
   
   
    -->
    
   
  </body>
  
</html>
