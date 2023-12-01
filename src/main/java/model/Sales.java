package model;

//これは、販売データを扱うための販売モデルです。
public class Sales {
	
	private String salesDate;
	private int productCode;
	private String productName;
	private int quantity;
	private String registerDateTime;
	private String updateDateTime;
	
	
	
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



	public String getSalesDate() {
		return salesDate;
	}



	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
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



	@Override
	public String toString() {
		return "Sales [salesDate=" + salesDate + ", productCode=" + productCode + ", productName=" + productName
				+ ", quantity=" + quantity + ", registerDateTime=" + registerDateTime + ", updateDateTime="
				+ updateDateTime + "]";
	}
}
