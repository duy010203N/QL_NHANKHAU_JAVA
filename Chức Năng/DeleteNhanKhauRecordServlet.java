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

@WebServlet("/DeleteNhanKhauRecordServlet")
public class DeleteNhanKhauRecordServlet extends HttpServlet {

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
            String maNhanKhau = request.getParameter("ma_nhan_khau"); 

if (maNhanKhau != null && !maNhanKhau.isEmpty()) {

  String sql = "DELETE FROM nhan_khau WHERE ma_nhan_khau = ?";
  
  PreparedStatement stmt = con.prepareStatement(sql);  

  stmt.setString(1, maNhanKhau);
   
  int rowsDeleted = stmt.executeUpdate();

  if (rowsDeleted > 0) {
    out.println("Xóa nhân khẩu thành công!");

  } else {
    out.println("Không tìm thấy nhân khẩu có mã " + maNhanKhau + " để xóa.");  
  }

} else {
  out.println("Lỗi: Mã nhân khẩu cần xóa không được cung cấp.");
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