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
@WebServlet("/EditQHRecordServlet")
public class EditQHRecordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
        Connection con;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Form Sửa Dữ Liệu Quan Hệ Với Chủ Hộ</title>"); 
        out.println("</head>");
        
        out.println("<body>");
        
        String MQH = request.getParameter("ma_quan_he");
        
        out.println("<h2>Form Sửa Dữ Liệu Hộ Khẩu</h2>");
        out.println("<form action='EditQHRecordServlet' method='post'>");
       
        out.println("  <input type='hidden' name='ma_quan_he' value='" + MQH+ "'/>");
        
        out.println("  <label for='ten_quan_he'>Tên Quan Hệ  :</label>");
        out.println("  <input type='text' id='ten_quan_he' name='ten_quan_he' required><br>");

        out.println("  <input type='submit' value='Lưu'>");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");

    // Lấy dữ liệu từ form
    String MQH = request.getParameter("ma_quan_he");
    String TQH = request.getParameter("ten_quan_he");
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
    String sql = "UPDATE quan_he_voi_chu_ho SET ten_quan_he = ? " + 
                "WHERE ma_quan_he = ?";
    
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
    
    statement.setString(1, TQH);
    statement.setString(2, MQH); 


    int rowsUpdated = statement.executeUpdate();

    if(rowsUpdated > 0) {
        PrintWriter out = response.getWriter();
        out.println("Cập nhật thành công!");
    } else {
        PrintWriter out = response.getWriter();
        out.println("Không tìm thấy mã quan hệ!");
    }
            }
} catch (Exception e) {
    e.printStackTrace();
    response.getWriter().println(e.getMessage()); 
}
    }
}
