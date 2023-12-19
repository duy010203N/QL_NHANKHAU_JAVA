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

@WebServlet("/AddDiaChiServlet")
public class AddDiaChiServlet    extends HttpServlet {
    // Servlet code


    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String maDiaChi = request.getParameter("ma_dia_chi");
String soNha = request.getParameter("so_nha");
String duong = request.getParameter("duong");
String phuong = request.getParameter("phuong");
String quan = request.getParameter("quan");
String tinhThanhPho = request.getParameter("tinh_thanh_pho");


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nhankhau", "root", "12345678");

            String sql = "INSERT INTO dia_chi (ma_dia_chi, so_nha, duong, phuong, quan, tinh_thanh_pho) " + 
             "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {

  pstmt.setString(1, maDiaChi);  

  pstmt.setString(2, soNha);

  pstmt.setString(3, duong);
   
  pstmt.setString(4, phuong);  

  pstmt.setString(5, quan);

  pstmt.setString(6, tinhThanhPho);

  pstmt.executeUpdate();

}

            con.close();

            // Use a context-relative URL for redirection
            response.sendRedirect("/Xem?table=dia_chi");
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}
