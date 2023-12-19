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
@WebServlet("/EditNhanKhauRecordServlet")
public class EditNhanKhauRecordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
        Connection con;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Form Sửa Dữ Liệu Nhân Khẩu</title>"); 
        out.println("</head>");
        
        out.println("<body>");
        
        String soSHK = request.getParameter("so_so_ho_khau");
        String maNhanKhau = request.getParameter("ma_nhan_khau");

out.println("<h2>Form Sửa Dữ Liệu Nhân Khẩu</h2>");
out.println("<form action='EditNhanKhauRecordServlet' method='post'>");

out.println("  <input type='hidden' name='so_so_ho_khau' value='" + soSHK + "'/>");

out.println("  <label for='ma_nhan_khau'>Mã Nhân Khẩu:</label>");
out.println("  <input type='text' id='ma_nhan_khau' name='ma_nhan_khau' required><br>");

out.println("  <label for='ho_ten'>Họ Tên:</label>");
out.println("  <input type='text' id='ho_ten' name='ho_ten' required><br>");

out.println("  <label for='phai'>Phái:</label>");
out.println("  <input type='text' id='phai' name='phai' required><br>");

out.println("  <label for='ngay_sinh'>Ngày Sinh:</label>");
out.println("  <input type='date' id='ngay_sinh' name='ngay_sinh' required><br>");

out.println("  <label for='ma_quan_he'>Mã Quan Hệ:</label>");
out.println("  <input type='text' id='ma_quan_he' name='ma_quan_he' required><br><br>");

out.println("<input type='submit' value='Lưu Chỉnh Sửa'>");
out.println("</form>");

        
        out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");

    // Lấy dữ liệu từ form
    
    String maNhanKhau = request.getParameter("ma_nhan_khau");
    String soSHK = request.getParameter("so_so_ho_khau");
        String hoTen = request.getParameter("ho_ten");
        String phai = request.getParameter("phai");
        String ngaySinh = request.getParameter("ngay_sinh");
      
        String maQuanHe = request.getParameter("ma_quan_he");
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
     String sql = "UPDATE nhan_khau SET ho_ten = ?, phai = ?, ngay_sinh = ?, ma_quan_he = ? "
                    + "WHERE ma_nhan_khau = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
    
                 statement.setString(1, hoTen);
                statement.setString(2, phai);
                statement.setString(3, ngaySinh);
                statement.setString(4, maQuanHe);
                statement.setString(5, maNhanKhau);
           


    int rowsUpdated = statement.executeUpdate();

    if(rowsUpdated > 0) {
        PrintWriter out = response.getWriter();
        out.println("Cập nhật thành công!");
    } else {
        PrintWriter out = response.getWriter();
        out.println("Không tìm thấy nhân khẩu!");
    }
            }
} catch (Exception e) {
    e.printStackTrace();
    response.getWriter().println(e.getMessage()); 
}
    }
}

    