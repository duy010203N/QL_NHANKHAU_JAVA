import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteQHRecordServlet")
public class DeleteQHRecordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nhankhau", "root", "12345678");

            // Lấy số sổ hộ khẩu cần xóa từ request 
            String ma_quan_he = request.getParameter("ma_quan_he");

            // Kiểm tra xem số sổ hộ khẩu có tồn tại không
            if (ma_quan_he != null && !ma_quan_he.isEmpty()) {

                // Tạo câu lệnh SQL để xóa hộ khẩu
                String sql = "DELETE FROM quan_he_voi_chu_ho WHERE ma_quan_he = ?";
                
                // Tạo đối tượng PreparedStatement
                PreparedStatement statement = con.prepareStatement(sql);
                
                // Set giá trị cho tham số
                statement.setString(1, ma_quan_he);
                
                // Thực hiện xóa
                int rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) {
                    out.println("Xóa quan hệ với chủ hộ thành công!");
                } else {
                    out.println("Không tìm thấy mã quan hệ  " + ma_quan_he + " để xóa.");
                }

            } else {
                out.println("Lỗi: Mã quan hệ cần xóa không được cung cấp."); 
            }

            con.close();
            
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }

        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}