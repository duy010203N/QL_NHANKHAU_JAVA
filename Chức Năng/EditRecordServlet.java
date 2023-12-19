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
@WebServlet("/EditRecordServlet")
public class EditRecordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
        Connection con;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Form Sửa Dữ Liệu Hộ Khẩu</title>"); 
        out.println("</head>");
        
        out.println("<body>");
        
        String soSHK = request.getParameter("so_so_ho_khau");
        
        out.println("<h2>Form Sửa Dữ Liệu Hộ Khẩu</h2>");
        out.println("<form action='EditRecordServlet' method='post'>");
       
        out.println("  <input type='hidden' name='so_so_ho_khau' value='" + soSHK+ "'/>");
        
        out.println("  <label for='ma_dia_chi'>Mã Địa Chỉ:</label>");
        out.println("  <input type='text' id='ma_dia_chi' name='ma_dia_chi' required><br>");

        out.println("  <label for='ngay_lap_so'>Ngày Lập Sổ:</label>"); 
        out.println("  <input type='date' id='ngay_lap_so' name='ngay_lap_so' required><br>");

        out.println("  <label for='noi_cap'>Nơi Cấp:</label>");
        out.println("  <input type='text' id='noi_cap' name='noi_cap' required><br><br>");  

        out.println("  <input type='submit' value='Lưu'>");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");

    // Lấy dữ liệu từ form
    String soSHK = request.getParameter("so_so_ho_khau");
    String maDiaChi = request.getParameter("ma_dia_chi");
    String ngayLapSo = String.valueOf(request.getParameter("ngay_lap_so"));
    String noiCap = request.getParameter("noi_cap");
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
    String sql = "UPDATE ho_khau SET ma_dia_chi = ?, ngay_lap_so = ?, noi_cap = ? " + 
                "WHERE so_so_ho_khau = ?";
    
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
    
    statement.setString(1, maDiaChi);
    statement.setString(2, ngayLapSo); 
    statement.setString(3, noiCap);
    statement.setString(4, soSHK);

    int rowsUpdated = statement.executeUpdate();

    if(rowsUpdated > 0) {
        PrintWriter out = response.getWriter();
        out.println("Cập nhật thành công!");
    } else {
        PrintWriter out = response.getWriter();
        out.println("Không tìm thấy sổ hộ khẩu!");
    }
            }
} catch (Exception e) {
    e.printStackTrace();
    response.getWriter().println(e.getMessage()); 
}
    }
}
