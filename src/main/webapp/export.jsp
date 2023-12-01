<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Export</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</head>
<body>
 <div class="card" style="width:30rem; margin:10% auto;">
<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
      <h1 class="display-4">CSVダウンロード</h1>
      <h6> <c:out value="${errors} "/> </h6>
      
      <div class="card"  style="width:18rem; margin:0 auto;">
          <div class="card-body">
          <form method="post" action="export">
  			<label for="month">年月 &nbsp;&nbsp;  </label>
			<input type="month" id="month" name="month" required/> &nbsp;&nbsp;
  			<button type="submit" class="btn btn-outline-primary">指定年月商品別売上集計CSV</button>
			</form>
			<br>
			<button class="btn btn-lg btn-block btn-primary" id="submit" onclick="location.href ='exportall'" >商品別売上集計CSV</button>
            
          </div>
      </div>
      </div>
	 <br>
</div>
</body>
</html>