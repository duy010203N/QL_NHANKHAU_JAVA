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

@WebServlet("/DeleteDiaChiRecordServlet")
public class DeleteDiaChiRecordServlet extends HttpServlet {

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
            String ma_dia_chi = request.getParameter("ma_dia_chi");

            // Kiểm tra xem số sổ hộ khẩu có tồn tại không
            if (ma_dia_chi != null && !ma_dia_chi.isEmpty()) {

                // Tạo câu lệnh SQL để xóa hộ khẩu
                String sql = "DELETE FROM dia_chi WHERE ma_dia_chi = ?";
                
                // Tạo đối tượng PreparedStatement
                PreparedStatement statement = con.prepareStatement(sql);
                
                // Set giá trị cho tham số
                statement.setString(1, ma_dia_chi);
                
                // Thực hiện xóa
                int rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) {
                    out.println("Xóa địa chỉ thành công!");
                } else {
                    out.println("Không tìm thấy mã địa chỉ  " + ma_dia_chi + " để xóa.");
                }

            } else {
                out.println("Lỗi: Mã địa chỉ cần xóa không được cung cấp."); 
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