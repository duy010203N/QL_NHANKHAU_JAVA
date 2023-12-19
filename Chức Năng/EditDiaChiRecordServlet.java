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
@WebServlet("/EditDiaChiRecordServlet")
public class EditDiaChiRecordServlet extends HttpServlet {

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
        
        String MDC = request.getParameter("ma_dia_chi");

out.println("<h2>Form Sửa Dữ Liệu Địa Chỉ</h2>");
out.println("<form action='EditDiaChiRecordServlet' method='post'>");

out.println("  <input type='hidden' name='ma_dia_chi' value='" + MDC + "'/>");

out.println("  <label for='ma_dia_chi'>Mã Địa Chỉ:</label>");
out.println("  <input type='text' id='ma_dia_chi' name='ma_dia_chi' required><br>");

out.println("  <label for='so_nha'>Số nhà:</label>");
out.println("  <input type='text' id='so_nha' name='so_nha' required><br>");

out.println("  <label for='duong'>Đường:</label>");
out.println("  <input type='text' id='duong' name='duong' required><br>");

out.println("  <label for='phuong'>Phường:</label>");
out.println("  <input type='text' id='phuong' name='phuong' required><br>");

out.println("  <label for='quan'>Quận:</label>");
out.println("  <input type='text' id='quan' name='quan' required><br><br>");

out.println("  <label for='tinh_thanh_pho'>Tỉnh thành phố:</label>");
out.println("  <input type='text' id='tinh_thanh_pho' name='tinh_thanh_pho' required><br><br>");

out.println("<input type='submit' value='Lưu Chỉnh Sửa'>");
out.println("</form>");

        
        out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");

    // Lấy dữ liệu từ form
    
    String MDC = request.getParameter("ma_dia_chi");
    String SN = request.getParameter("so_nha");
        String D = request.getParameter("duong");
        String P = request.getParameter("phuong");
        String Q = request.getParameter("quan");
            String TTP = request.getParameter("tinh_thanh_pho");

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
     String sql = "UPDATE dia_chi SET so_nha = ?, duong = ?, phuong = ?, quan = ?, tinh_thanh_pho = ? "
                    + "WHERE ma_dia_chi = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                                
                 statement.setString(1, SN);
                statement.setString(2, D);
                statement.setString(3, P);
                statement.setString(4, Q);
                statement.setString(5, TTP);
                statement.setString(6, MDC);


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

    