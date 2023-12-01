<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Sales</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
<h1>売上登錄</h1>

<h3>売上日: ${todaysdate}  <button class="btn btn-lg btn-primary" id="submit" onclick="location.href ='search'" >製品を検索</button> </h3>

<form method="post" action="addsales">
	<label for="productcode">商品名 &nbsp;&nbsp;  </label>
	<select name="productcode" id="productcode" class="form-select">
	<c:forEach items="${products}" var="product">
  	<option value="${product.productCode} ${product.productName}">${product.productName}</option>
  </c:forEach>
</select>
	<label for="productprice">数量 &nbsp;&nbsp;  </label>
	<input type="number" id="productquantity" name="productquantity"  min="1" max="1000000000" required/> &nbsp;&nbsp;
	<button type="submit" class="btn btn-outline-primary" >追加</button> <br><br><br>
</form>
<h3> <c:out value="${errors} "/> </h3>
<table class="table-responsive table-bordered col-5">
	  <thead class="thead-dark">
	    <tr>
	      <th scope="col" align="center" class="col-2">商品名</th>
	      <th scope="col" align="center" class="col-5">数量</th>
	    </tr>
	  </thead>
	  <tbody>
	  
	  <c:forEach items="${sales}" var="sale">
	  
	      <tr>
	      <td scope="col" align="center"><c:out value="${sale.productName} "/></td>
	      <td scope="col" align="left"><c:out value="${sale.quantity} "/></td>
	      </td>
	    </tr>
	    </c:forEach>
	    
	  </tbody>
	</table>
	<br>
	<form method="post" action="addsales">
	<input type="hidden" id="savefield" name="savefield" >
  	
  	<input type="submit" name="save" value="登録" onclick="saveValueSet()" class="btn btn-outline-primary"> &nbsp;&nbsp;
	</form>
	<br>
	<table class="table-responsive table-bordered col-5">
	  <thead class="thead-dark">
	    <tr>
	      <th scope="col" align="center" class="col-2">商品コード</th>
	      <th scope="col" align="center" class="col-5">商品名</th>
	    </tr>
	  </thead>
	  <tbody>
	  
	  <c:forEach items="${todayssales}" var="todayssale">
	      <tr>
	      <td scope="col" align="left"><c:out value="${todayssale.productName} "/></td>
	      <td scope="col" align="right"><c:out value="${todayssale.quantity} "/></td>
	    </tr>
	    </c:forEach>
	    
	  </tbody>
	</table>
	
	<script type="text/javascript">

	 function saveValueSet() {
		 var elem = document.getElementById("savefield");
		 elem.value = "Save";
		}
	</script>

</body>
</html>