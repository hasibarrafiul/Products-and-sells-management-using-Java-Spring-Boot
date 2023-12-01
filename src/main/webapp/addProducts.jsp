<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Add a product</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</head>
<body
<div style="position:absolute; left:40%; top:20%;"> 
	<h1>商品登録</h1>
	<h6> <c:out value="${errors} "/> </h6>
	<form method="post" action="addproduct">
	 <div class="form-group" style="left: 50%; margin:0 auto;">
	 <div class="form-outline w-100">
		<label for="productname">商品名 &nbsp;&nbsp;  </label>
		<input type="text" id="productname" name="productname" class="form-control" maxlength="50" required/>
	</div>
	<div class="form-outline w-50">
		<label for="productprice">単価 &nbsp;&nbsp;  </label>
		<input type="number" id="productprice" name="productprice" class="form-control" min="1" max="1000000000" required/> &nbsp;&nbsp;
		
	</div>
	<button type="submit" class="btn btn-outline-primary" style="float: right;">登録</button> <br><br><br>
	</div>
	</form>
	<button class="btn btn-lg btn-primary" id="submit" onclick="location.href ='search'" >製品を検索</button>
	</div>
</body>
</html>