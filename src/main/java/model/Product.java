package model;

import java.text.NumberFormat;
import java.util.Locale;

//これはゲッターとセッターを備えた製品クラスです。
public class Product {
	private int productCode;
	private String productName;
	private int price;
	private String registerDateTime;
	private String updateDateTime;
	private String deleteDateTime;
	
	
	/**
	* @return このメソッドは整数を受け取り、ゼロを自動入力して返します。
	*/
	public String getProductCode() {
		return String.format("%03d",productCode);
	}



	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getPrice() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.JAPAN);
		String priceString = formatter.format(price);
		return priceString;
	}



	public void setPrice(int price) {
		this.price = price;
	}



	public String getRegisterDateTime() {
		return registerDateTime;
	}



	public void setRegisterDateTime(String registerDateTime) {
		this.registerDateTime = registerDateTime;
	}



	public String getUpdateDateTime() {
		return updateDateTime;
	}



	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}



	public String getDeleteDateTime() {
		return deleteDateTime;
	}



	public void setDeleteDateTime(String deleteDateTime) {
		this.deleteDateTime = deleteDateTime;
	}



	@Override
	public String toString() {
		return "Product [productCode=" + productCode + ", productName=" + productName + ", price=" + price + ", registerDateTime="
				+ registerDateTime + "updateDateTime" + updateDateTime + "deleteDateTime" + deleteDateTime + "]";
	}
	
	

}
