import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddQHRecordServlet")
public class AddQHRecordServlet  extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String MQH = request.getParameter("ma_quan_he");
        String TQH = request.getParameter("ten_quan_he");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nhankhau", "root", "12345678");

            String sql = "INSERT INTO quan_he_voi_chu_ho (ma_quan_he, ten_quan_he) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, MQH);
            pstmt.setString(2, TQH);


            pstmt.executeUpdate();

            pstmt.close();
            con.close();

            response.sendRedirect("quanhevoichuho?table=quan_he_voi_chu_ho"); // Chuyển về trang danh sách sau khi thêm
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}
