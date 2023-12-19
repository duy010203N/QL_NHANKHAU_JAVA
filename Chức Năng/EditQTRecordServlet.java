import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/EditQTRecordServlet")
public class EditQTRecordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
        Connection con;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Form Sửa Dữ Liệu Địa Chỉ</title>"); 
        out.println("</head>");
        
        out.println("<body>");
        
        String MNK = request.getParameter("ma_nhan_khau");


out.println("<h2>Form Sửa Dữ Liệu Địa Chỉ</h2>");
out.println("<form action='EditQTRecordServlet' method='post'>");

out.println("  <input type='hidden' name='ma_nhan_khau' value='" + MNK + "'/>");


out.println("  <label for='ngay_chuyen_den'>Ngày Chuyển Đến:</label>");
out.println("  <input type='date' id='ngay_chuyen_den' name='ngay_chuyen_den' required><br>");

out.println("  <label for='ma_dia_chi_noi_chuyen_den'>Mã Địa Chỉ Chuyển Đến:</label>");
out.println("  <input type='text' id='ma_dia_chi_noi_chuyen_den' name='ma_dia_chi_noi_chuyen_den' required><br>");

out.println("  <label for='ly_do_den'>Lý Do Đến:</label>");
out.println("  <input type='text' id='ly_do_den' name='ly_do_den' required><br><br>");

out.println("<input type='submit' value='Lưu Chỉnh Sửa'>");
out.println("</form>");

        
        out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");

    // Lấy dữ liệu từ form
    
    String MNK = request.getParameter("ma_nhan_khau");
    String NCD = String.valueOf(request.getParameter("ngay_chuyen_den"));
        String MDCNCD = request.getParameter("ma_dia_chi_noi_chuyen_den");
        String LDD = request.getParameter("ly_do_den");

    try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi tải driver MySQL: " + e.getMessage());
            return;
        }
    // Kết nối đến cơ sở dữ liệu
        String jdbcUrl = "jdbc:mysql://localhost:3306/nhankhau";
        String username = "root";
        String password = "12345678";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

    // Câu lệnh SQL update
     String sql = "UPDATE qua_trinh_di_chuyen SET ngay_chuyen_den = ?, ma_dia_chi_noi_chuyen_den = ?, ly_do_den = ? "
                    + "WHERE ma_nhan_khau = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                           
              
                statement.setString(1, NCD);
                statement.setString(2, MDCNCD);
                statement.setString(3, LDD);
            statement.setString(4, MNK);


    int rowsUpdated = statement.executeUpdate();

    if(rowsUpdated > 0) {
        PrintWriter out = response.getWriter();
        out.println("Cập nhật thành công!");
    } else {
        PrintWriter out = response.getWriter();
        out.println("Không tìm thấy địa chỉ!");
    }
            }
} catch (Exception e) {
    e.printStackTrace();
    response.getWriter().println(e.getMessage()); 
}
    }
}

    