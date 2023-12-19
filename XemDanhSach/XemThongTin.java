import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/XemThongTin")
public class XemThongTin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public XemThongTin() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nhankhau", "root", "12345678");

            String tableName = req.getParameter("table");
            String searchSHK = req.getParameter("searchso_so_ho_khau");

             out.println("<!DOCTYPE html>");
out.println("<html>");
out.println("<head>");
out.println("<meta charset='UTF-8'>");
out.println("<style>");
out.println("body {display: flex; flex-direction: column; align-items: center; justify-content: center; height: 140vh; margin: 0; margin-bottom: 0;}");
out.println("table {border-collapse: collapse; width: 80%;}");
out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
out.println("th {background-color: #f2f2f2;}");
out.println("nav { background-color: #333; overflow: hidden; }");
out.println("nav a { float: left; display: block; color: white; text-align: center; padding: 14px 16px; text-decoration: none; }");
out.println("nav a#searchButton { background-color: #4CAF50; }"); // Green color for Tìm Kiếm
out.println("nav a#addButton { background-color: #3498db; }"); // Blue color for Thêm Mới
out.println("nav a:hover { background-color: #ddd; color: black; }");
out.println("</style>");
out.println("<script>");
out.println("function showTable(tableName) {");
out.println("  window.location.href = 'XemThongTin?table=' + encodeURIComponent(tableName);");
out.println("}");
out.println("</script>");
out.println("</head>");
out.println("<body>");
out.println("<h2 style='color: #fff; background: linear-gradient(to right, #4CAF50, #336699); padding: 15px; border-radius: 8px; border: 2px solid #336699; text-align: center;'>HỘ KHẨU</h2>");

// Menu
out.println("<nav>");
out.println("<a id='addButton' href='#' onclick='showAddForm()'>Thêm Mới</a>");
out.println("<a id='searchButton' href='#' onclick='showSearchForm()'>Tìm Kiếm</a>");  
out.println("</nav>");
out.println("<body>");

out.println("<div id='searchForm' style='display:none;'>");
out.println("<h2>Tìm Kiếm Thông Tin Hộ Khẩu</h2>");
out.println("<form method='get'>");
out.println(" <label for='searchso_so_ho_khau'>Số Sổ Hộ Khẩu:</label>");
out.println(" <input type='text' id='searchso_so_ho_khau' name='searchso_so_ho_khau'>");
out.println(" <input type='submit' value='Tìm Kiếm'>");
out.println("</form>");
out.println("</div>");

out.println("<script>");
out.println("function showSearchForm() {");
out.println("  var searchForm = document.getElementById('searchForm');");
out.println("  searchForm.style.display = 'block';");
out.println("}");
out.println("</script>");


out.println("<div id='addForm' style='display:none;'>");
out.println("<h3>Thêm mới hộ khẩu</h3>");
out.println("<form method='post' action='AddRecordServlet'>");
out.println("Số sổ hộ khẩu: <input type='text' name='so_so_ho_khau'><br>");
out.println("Mã địa chỉ: <input type='text' name='ma_dia_chi'><br>");
out.println("Ngày lập sổ: <input type='date' name='ngay_lap_so'><br>");
out.println("Nơi cấp: <input type='text' name='noi_cap'><br>");
out.println("<input type='submit' value='Thêm Mới'>");
out.println("</form>");
out.println("</div>");

out.println("<script>");
out.println("function showAddForm() {");
out.println("  var addForm = document.getElementById('addForm');");
out.println("  addForm.style.display = 'block';");
out.println("}");
out.println("</script>");
            Statement stmt = null;

            try {
                if (tableName == null || tableName.isEmpty() || tableName.equals("ho_khau")) {
                    // Hiển thị thông tin hộ khẩu
                    stmt = con.createStatement();
                    String sql1 = "SELECT * FROM ho_khau";
        String sql2 = "SELECT * FROM ho_khau WHERE so_so_ho_khau LIKE '%" + searchSHK + "%'";
                    ResultSet rs;
                     if (searchSHK != null && !searchSHK.isEmpty()) {
            rs = stmt.executeQuery(sql2);
        } else {
            rs = stmt.executeQuery(sql1);
        }

                    out.println("<table border=1 width=50% height=50%>");
                    out.println("<tr><th>Số sổ hộ khẩu</th><th>Mã địa chỉ</th><th>Ngày lập sổ</th><th>Nơi cấp</th></tr>");

                    while (rs.next()) {
                        String SHK = rs.getString("so_so_ho_khau");
                        String MDC = rs.getString("ma_dia_chi");
                        Date ngayLapSo = rs.getDate("ngay_lap_so");
                        String noiCap = rs.getString("noi_cap");

                        out.println("<tr><td>" + SHK + "</td><td>" + MDC + "</td><td>" + ngayLapSo + "</td><td>" + noiCap
            + "</td><td><a href='EditRecordServlet?so_so_ho_khau=" + SHK + "'>Edit</a></td>"
            + "<td><a href='DeleteRecordServlet?so_so_ho_khau=" + SHK + "'>Delete</a></td></tr>");
                    }
                }
            } finally {
                if (stmt != null) {
                    stmt.close(); // Đóng Statement
                }
                con.close(); // Đóng Connection
            }

            out.println("</table>");
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
