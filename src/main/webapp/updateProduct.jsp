<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Product</title>

</head>
<body>


<h1>商品変更・削除</h1> <br>
<h6> <c:out value="${errors} "/> </h6>
商品一下 <c:out value="${product.productCode} "/> <br><br>
<form method="post" action="update">
  <label for="productName">商品名</label>
  <input type="text" id="productName" name="productname" value='${product.productName}' maxlength="50" required><br><br>
  <label for="price">単価&nbsp;&nbsp;&nbsp;&nbsp;</label>
  <input type="number" id="s1" name="productprice" min="1" max="1000000000" required><br><br>
  
  <input type="hidden" id="deletefield" name="deletefield" >
  
  <input type="submit" value="変更">
  
  <input type="submit" name="delete" value="削除" onclick="deleteValueSet()"> &nbsp;&nbsp;
</form>



<script type="text/javascript">
	 var elem = document.getElementById("s1");
	 var val = "${product.price}";
	 val = val.replace(/\D/g,'');
	 elem.value = parseInt(val);

	 function deleteValueSet() {
		 var elem = document.getElementById("deletefield");
		 elem.value = "Delete";
		}
	</script>
</body>
</html>