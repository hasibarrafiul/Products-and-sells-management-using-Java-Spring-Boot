<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>商品検索</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>

<body>
	<h1 style="text-align: center;"> 商品検索 </h1>
	<div class="text-center">
	<button class="btn btn-lg btn-primary" id="submit" onclick="location.href ='addproduct'" >製品を追加する</button>
	<button class="btn btn-lg btn-primary" id="submit" onclick="location.href ='addsales'" >追加販売</button>
	<button class="btn btn-lg btn-primary" id="submit" onclick="location.href ='export'" >輸出</button>
	</div>
	<br>
	<form method="get" action="search" class="form-inline">
	 <div class="form-group mb-2" style="left: 50%; margin:0 auto;">
	 
		<label for="search">商品名 &nbsp;&nbsp;  </label>
		<input type="text" id="search" name="search" class="form-control" maxlength="50"/> &nbsp;&nbsp;
		 <button type="submit" class="btn btn-outline-primary">検索</button>
		 
	</div>
	</form>
	
	<h3 style="text-align: center;"> <c:out value="${errors} "/> </h3>
	<table class="table-responsive table-bordered col-5" align="center">
	  <thead class="thead-dark">
	    <tr>
	      <th scope="col" align="center" class="col-2">商品コード</th>
	      <th scope="col" align="center" class="col-5">商品名</th>
	      <th scope="col" align="center" class="col-1">単価</th>
	       <th scope="col" align="center" class="col-1">操作</th>
	    </tr>
	  </thead>
	  <tbody>
	  
	  <c:forEach items="${products}" var="product">
	  
	      <tr>
	      <td scope="col" align="center"><c:out value="${product.productCode} "/></td>
	      <td scope="col" align="left"><c:out value="${product.productName} "/></td>
	      <td scope="col" align="right"><c:out value="${product.price} "/></td>
	      <td scope="col" align="center">
	      <form method="get" action="update">
	        <button type="submit" class="btn btn-outline-primary" name="product_code" value= "${product.productCode}">操作</button>
	        </form>
	      </td>
	    </tr>
	    </c:forEach>
	    
	  </tbody>
	</table>
</body>
</html>