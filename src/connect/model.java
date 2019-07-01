package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import manager.Save;

public class model {
	Connection con;
	
	public model() throws Exception {
		con=ConDb.getConnection();
	}

	public void Insert(Save sa) throws Exception {
		String sql="insert into food_m values "
				+ " (?,?,?,?,?,?,?)";
		
		PreparedStatement ps1 = con.prepareStatement(sql);
		ps1.setString(1, sa.getArea());
		ps1.setString(2, sa.getJong());
		ps1.setString(3, sa.getAdname());
		ps1.setString(4, sa.getAddres());
		ps1.setString(5, sa.getFoodna());
		ps1.setInt(6, sa.getPrice());
		ps1.setString(7, sa.getImg());
		
		ps1.executeUpdate();
		
		ps1.close();
	}

	public void Modeify(Save sa) throws Exception {
		String sql="UPDATE food_m set area= ?, jong= ?, ad_name =?,"+
				" addres = ?, price= ? where food_na=?";
		
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, sa.getArea());
		ps.setString(2, sa.getJong());
		ps.setString(3, sa.getAdname());
		ps.setString(4, sa.getAddres());
		ps.setInt(5, sa.getPrice());
		ps.setString(6, sa.getFoodna());
		
		ps.executeUpdate();
		ps.close();
		
	}

	public ArrayList Search(String str, int idx) throws Exception {
		String[] key= {"area","jong","ad_name","food_na"};
		String sql="select area, jong, ad_name, addres,food_na ,price  from food_m "
				+ "where "+ key[idx] +" like '%"+str+"%' ";
		//"지역","종륙","매장명","주소","음식이름","가격"
		
		PreparedStatement ps= con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		ArrayList data=new ArrayList();
		
		while (rs.next()) {
			ArrayList temp=new ArrayList();
			temp.add(rs.getString("AREA"));
			temp.add(rs.getString("JONG"));
			temp.add(rs.getString("AD_NAME"));
			temp.add(rs.getString("ADDRES"));
			temp.add(rs.getString("FOOD_NA"));
			temp.add(rs.getInt("PRICE"));
			data.add(temp);
			
		}
		rs.close();
		ps.close();
	
		return data;
		
	}
	
	public Save selectbypk(String sa) throws Exception {
		System.out.println("와");
		Save data=new Save();
		String sql="select * from food_m where food_na="+"'"+sa+"'";
		PreparedStatement ps= con.prepareStatement(sql);
		
		ResultSet rs=ps.executeQuery();
		
		while (rs.next()) {
			data.setArea(rs.getString("area"));
			data.setJong(rs.getString("jong"));
			data.setAdname(rs.getString("ad_name"));
			data.setAddres(rs.getString("addres"));
			data.setFoodna(rs.getString("food_na"));
			data.setPrice(Integer.parseInt(rs.getString("price")));
			
			data.setImg(rs.getString("imgfname"));
			
		}
		rs.close();
		ps.close();
		
		return data;
		
		
		
	}

	public void Delete(String str) throws Exception {
		
		String sql="delete food_m where food_na='"+str+"'";
		System.out.println(str);
		PreparedStatement ps=con.prepareStatement(sql);
		
		ps.executeUpdate();
		ps.close();
		
	}
}
