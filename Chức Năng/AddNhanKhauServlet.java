import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddNhanKhauServlet")
public class AddNhanKhauServlet    extends HttpServlet {
    // Servlet code


    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String maNhanKhau = request.getParameter("ma_nhan_khau");

String hoTen = request.getParameter("ho_ten");  
String phai = request.getParameter("phai");
String ngaySinh = request.getParameter("ngay_sinh");
String soSoHoKhau = request.getParameter("so_so_ho_khau");
String maQuanHe = request.getParameter("ma_quan_he");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nhankhau", "root", "12345678");

            String sql = "INSERT INTO nhan_khau (ma_nhan_khau, ho_ten, phai, ngay_sinh, so_so_ho_khau, ma_quan_he) " + 
             "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {

  pstmt.setString(1, maNhanKhau);  

  pstmt.setString(2, hoTen);

  pstmt.setString(3, phai);
   
  // Parse ngaySinh 
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  Date parsedDate = sdf.parse(ngaySinh);
  java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
   
  pstmt.setDate(4, sqlDate);  

  pstmt.setString(5, soSoHoKhau);

  pstmt.setString(6, maQuanHe);

  pstmt.executeUpdate();

}

            con.close();

            // Use a context-relative URL for redirection
            response.sendRedirect(request.getContextPath() + "/Xem?table=nhan_khau");
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}
