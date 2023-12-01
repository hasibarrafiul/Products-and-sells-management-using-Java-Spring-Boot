package dao;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DbConnect;
import model.Product;
import model.Sales;
import model.TotalSellByProduct;

public class ProductDao {
	public Connection connection;
	
	
	//これにより、データベースへの接続が作成されます。
	public ProductDao() throws ClassNotFoundException, SQLException{
		connection = DbConnect.getConnection();
	}
		//これにより、データベースからすべての値が返されます。
	
		/**
		* @param List<products> これは、product クラスの product 配列リストです。
		* @param products これは、jsp ファイルがレンダリングするために返される製品の配列リストです。
		 * @throws SQLException 
		*/
		public List<Product> getAllProducts() throws SQLException{
			
			List<Product> products = new ArrayList<Product>();
			
				java.sql.Statement statement = connection.createStatement();
				
				ResultSet rs = statement.executeQuery("SELECT product_code, product_name, price, "
						+ "register_datetime, update_datetime, delete_datetime FROM m_product WHERE delete_datetime IS NULL ORDER BY product_code ASC");
				
				while(rs.next()) {
					Product product = new Product();
					product.setProductCode(rs.getInt("product_code"));
					product.setProductName(rs.getString("product_name"));
					product.setPrice(rs.getInt("price"));
					product.setRegisterDateTime(rs.getString("register_datetime"));
					product.setUpdateDateTime(rs.getString("update_datetime"));
					product.setDeleteDateTime(rs.getString("delete_datetime"));
					
					products.add(product);
				}
				
				return products;
			
		}
		
		//これにより、データベースから検索された値のみが返されます。
		public List<Product> getSearchedProducts(String searchKey) throws SQLException{
			PreparedStatement pst  = null;
			List<Product> products = new ArrayList<Product>();
				
				pst = connection.prepareStatement("SELECT product_code, product_name, price, "
						+ "register_datetime, update_datetime, delete_datetime FROM m_product WHERE product_name LIKE ? AND delete_datetime IS NULL ORDER BY product_code ASC");
				pst.setString(1, "%" + searchKey + "%");
				
				ResultSet rs = pst.executeQuery();
				
//				if (!rs.isBeforeFirst() ) {    
//				    System.out.println("No data"); 
//				}
				
				while(rs.next()) {
					Product product = new Product();
					product.setProductCode(rs.getInt("product_code"));
					product.setProductName(rs.getString("product_name"));
					product.setPrice(rs.getInt("price"));
					product.setRegisterDateTime(rs.getString("register_datetime"));
					product.setUpdateDateTime(rs.getString("update_datetime"));
					product.setDeleteDateTime(rs.getString("delete_datetime"));
					
					products.add(product);
				}
				
			
				return products;
			
		}
		
		//これにより新しい製品が追加されます
		public void addProduct(String productName, int productPrice) throws SQLException {
			PreparedStatement pst = connection.prepareStatement("INSERT INTO m_product(product_name, price, register_datetime, update_datetime) values(?, ?, NOW(), NOW())");
			
			pst.setString(1, productName);
			pst.setInt(2, productPrice);
			
			pst.executeUpdate();
		}
		
		//これは、製品コードで指定された製品のみを返します
		public Product getProduct(String productCode) throws SQLException{
			PreparedStatement pst  = null;
			Product product = new Product();
			int code = Integer.parseInt(productCode);
			
			pst = connection.prepareStatement("SELECT product_code, product_name, price, "
					+ "register_datetime, update_datetime, delete_datetime FROM m_product WHERE product_code = ?");
			pst.setInt(1, code);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				product.setProductCode(rs.getInt("product_code"));
				product.setProductName(rs.getString("product_name"));
				product.setPrice(rs.getInt("price"));
				product.setRegisterDateTime(rs.getString("register_datetime"));
				product.setUpdateDateTime(rs.getString("update_datetime"));
				product.setDeleteDateTime(rs.getString("delete_datetime"));
			}
			return product;
		}
		
		//このメソッドは、製品コードを使用して製品を更新します
		public int updateProduct(String productName, int productPrice, int productCode) throws SQLException {
			PreparedStatement pst = connection.prepareStatement("UPDATE m_product SET product_name = ?, price = ?, update_datetime = NOW() WHERE product_code = ? AND product_code NOT IN(SELECT product_code FROM t_sales) AND delete_datetime IS NULL");
			
			pst.setString(1, productName);
			pst.setInt(2, productPrice);
			pst.setInt(3, productCode);
			
			int checkIfUpdated = pst.executeUpdate();
			if(checkIfUpdated == 0) {
				return 0;
			} else {
				return 1;
			}
			
		}
		
		//このメソッドは、製品がすでに販売されている場合に名前のみを更新します。
		public int updateProductNameOnly(String productName, int productCode) throws SQLException {
			PreparedStatement pst = connection.prepareStatement("UPDATE m_product SET product_name = ?, update_datetime = NOW() WHERE product_code = ? AND delete_datetime IS NULL");
			
			pst.setString(1, productName);
			pst.setInt(2, productCode);
			
			pst.executeUpdate();
			
			int checkIfUpdated = pst.executeUpdate();
			if(checkIfUpdated == 0) {
				return 0;
			} else {
				return 1;
			}
		}
		
		//このメソッドは、削除日時を NOW() に設定して商品を削除します。
		public int deleteProduct(int productCode) throws SQLException {
			PreparedStatement pst = connection.prepareStatement("UPDATE m_product SET delete_datetime = NOW() WHERE product_code = ? AND delete_datetime IS NULL");
			
			pst.setInt(1, productCode);
			int checkIfUpdated = pst.executeUpdate();
			
			if(checkIfUpdated == 0) {
				return 0;
			} else {
				return 1;
			}
		}
		
		//このメソッドは、同時実行性をチェックするために製品のデータベースから updateDateTime を返します。
		public String getUpdateTime(int productCode) throws SQLException {
			PreparedStatement pst  = null;
			
			pst = connection.prepareStatement("SELECT update_datetime FROM m_product WHERE product_code = ?");
			pst.setInt(1, productCode);
			
			String updateTime = "";
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				updateTime = rs.getString("update_datetime");
			}
			return updateTime;
		}
		
		//このメソッドは、新しいセルを追加しようとする
		public boolean addSales(String date, int productCode, int quantity) throws SQLException {
			
			//商品が存在すればtrueを返し、そうでなければfalseを返す。
			
			PreparedStatement checkIfExists = connection.prepareStatement("SELECT product_name from m_product WHERE product_code = ? AND delete_datetime IS NULL");
			checkIfExists.setInt(1, productCode);
			ResultSet check = checkIfExists.executeQuery();
			if(check.next()) {
				
				PreparedStatement pst = connection.prepareStatement("INSERT INTO t_sales(sales_date, product_code, quantity, register_datetime, update_datetime) values(?, ?, ?, NOW(), NOW())");
				
				pst.setString(1, date);
				pst.setInt(2, productCode);
				pst.setInt(3, quantity);
				
				pst.executeUpdate();
				return true;
			} else {
				return false;
			}
		}
		
		//このメソッドは、すでに販売されている場合、数量を更新します。
		public void updateSales(String date, int productCode, int quantity) throws SQLException {
			PreparedStatement pst = connection.prepareStatement("UPDATE t_sales SET quantity=?, update_datetime=NOW() WHERE sales_date=? AND product_code=?");
			
			pst.setInt(1, quantity);
			pst.setString(2, date);
			pst.setInt(3, productCode);
			
			pst.executeUpdate();
		}
		
		//これはすべての販売データを返す
		public  List<Sales> getSales(String date) throws SQLException{
			List<Sales> sales = new ArrayList<Sales>();
			PreparedStatement pst  = null;
			
			pst = connection.prepareStatement("SELECT p.product_name, s.quantity FROM m_product p INNER JOIN t_sales s ON p.product_code = s.product_code WHERE s.sales_date=?");
			pst.setString(1, date);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Sales sale = new Sales();
				sale.setProductName(rs.getString("product_name"));
				sale.setQuantity(rs.getInt("quantity"));
				sales.add(sale);
			}
			return sales;
		}
		
		public boolean checkIfSold(int productCode) throws SQLException {
			PreparedStatement pst  = null;
			pst = connection.prepareStatement("SELECT sales_date FROM t_sales WHERE product_code=?");
			pst.setInt(1, productCode);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
			
		}
		
		public boolean checkIfDeleted(int productCode) throws SQLException {
			PreparedStatement pst  = null;
			pst = connection.prepareStatement("SELECT product_name FROM m_product WHERE product_code=? AND delete_datetime IS NOT NULL");
			pst.setInt(1, productCode);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
			
		}
		
		public  int getTotalSellByProduct(OutputStream outputStream) throws SQLException, IOException{
			List<TotalSellByProduct> totalSellByProduct = new ArrayList<TotalSellByProduct>();
			PreparedStatement pst  = null;
			
			pst = connection.prepareStatement("SELECT p.product_code, p.product_name, p.price, s.quantity, (p.price * s.quantity) AS amount"
					+ " from m_product p LEFT JOIN  t_sales s ON p.product_code = s.product_code"
					+ " ORDER BY product_code ASC;");
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				TotalSellByProduct tsbp = new TotalSellByProduct();
				tsbp.setProductCode(rs.getInt("product_code"));
				tsbp.setProductName(rs.getString("product_name"));
				tsbp.setUnitPrice(rs.getInt("price"));
				tsbp.setQuantity(rs.getInt("quantity"));
				tsbp.setAmount(rs.getInt("amount"));
				totalSellByProduct.add(tsbp);
			}
			if(totalSellByProduct.size() > 0) {
				export(totalSellByProduct, outputStream);
				return 1;
			} else {
				return 0;
			}
		}
		
		public int getTotalSellByMonth(String date, OutputStream outputStream) throws SQLException, IOException{
			List<TotalSellByProduct> totalSellByProduct = new ArrayList<TotalSellByProduct>();
			PreparedStatement pst  = null;
			
			pst = connection.prepareStatement("SELECT p.product_code, p.product_name, p.price, s.quantity, (p.price * s.quantity) AS amount"
					+ " from m_product p INNER JOIN  t_sales s ON p.product_code = s.product_code "
					+ "WHERE s.sales_date LIKE ?"
					+ " ORDER BY product_code ASC;");
			//System.out.println("date" + date);
			pst.setString(1, "" + date + "%");
			
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				TotalSellByProduct tsbp = new TotalSellByProduct();
				tsbp.setProductCode(rs.getInt("product_code"));
				tsbp.setProductName(rs.getString("product_name"));
				tsbp.setUnitPrice(rs.getInt("price"));
				tsbp.setQuantity(rs.getInt("quantity"));
				tsbp.setAmount(rs.getInt("amount"));
				totalSellByProduct.add(tsbp);
			}
			
			if(totalSellByProduct.size() > 0) {
				export(totalSellByProduct, outputStream);
				return 1;
			} else {
				return 0;
			}
		}
		
		public void export(List<TotalSellByProduct> totalSellByProduct, OutputStream outputStream) throws IOException {

	        String outputResult = "Product Code, product Name, Unit price, quantity, amount\n";
	        for (TotalSellByProduct sale : totalSellByProduct) {
	        	
	        	String productName = sale.getProductName();
	        	if (productName.contains("\"")) {
	        		productName = productName.replace("\"", "\"\"");
	            }
	            if (productName.contains(",")
	                    || productName.contains("\n")
	                    || productName.contains("'")
	                    || productName.contains("\\")
	                    || productName.contains("\"")) {
	            	productName = "\"" + productName + "\"";
	            }
	            
	        	outputResult = outputResult + sale.getProductCode() + "," + productName + "," + sale.getUnitPrice() + "," + sale.getQuantity() + "," + sale.getAmount() + "\n";
	        }
	        
	        outputStream.write(outputResult.getBytes());
	        outputStream.flush();
	        outputStream.close();
		}
		
}
