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

@WebServlet("/AddQTServlet")
public class AddQTServlet    extends HttpServlet {
    // Servlet code


    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String MNK = request.getParameter("ma_nhan_khau");

String NCD = request.getParameter("ngay_chuyen_den");
String MDCNCD = request.getParameter("ma_dia_chi_noi_chuyen_den");
String LDD = request.getParameter("ly_do_den");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nhankhau", "root", "12345678");

            String sql = "INSERT INTO qua_trinh_di_chuyen (ma_nhan_khau, ngay_chuyen_den, ma_dia_chi_noi_chuyen_den, ly_do_den) " + 
             "VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {

 // Set the values in the correct order
pstmt.setString(1, MNK); // ma_nhan_khau

// Parse ngay_chuyen_den
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Date parsedDate = sdf.parse(NCD);
java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
pstmt.setDate(2, sqlDate); // ngay_chuyen_den

pstmt.setString(3, MDCNCD); // ma_dia_chi_noi_chuyen_den
pstmt.setString(4, LDD); // ly_do_den

pstmt.executeUpdate();


  pstmt.executeUpdate();

}

            con.close();

            // Use a context-relative URL for redirection
            response.sendRedirect(request.getContextPath() + "/Xem?table=qua_trinh_di_chuyen");
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}
