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

@WebServlet("/AddRecordServlet")
public class AddRecordServlet  extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String soSoHoKhau = request.getParameter("so_so_ho_khau");
        String maDiaChi = request.getParameter("ma_dia_chi");
        String ngayLapSo = request.getParameter("ngay_lap_so");
        String noiCap = request.getParameter("noi_cap");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nhankhau", "root", "12345678");

            String sql = "INSERT INTO ho_khau (so_so_ho_khau, ma_dia_chi, ngay_lap_so, noi_cap) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, soSoHoKhau);
            pstmt.setString(2, maDiaChi);
            pstmt.setString(3, ngayLapSo);
            pstmt.setString(4, noiCap);

            pstmt.executeUpdate();

            pstmt.close();
            con.close();

            response.sendRedirect("XemThongTin?table=ho_khau"); // Chuyển về trang danh sách sau khi thêm
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}
