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

@WebServlet("/DiaChi")
public class DiaChi extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DiaChi() {
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
            String searchMDC = req.getParameter("searchma_dia_chi");

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
out.println("  window.location.href = 'quanhevoichuho?table=' + encodeURIComponent(tableName);");
out.println("}");
out.println("</script>");
out.println("</head>");
out.println("<body>");
out.println("<h2 style='color: #fff; background: linear-gradient(to right, #4CAF50, #336699); padding: 15px; border-radius: 8px; border: 2px solid #336699; text-align: center;'>ĐỊA CHỈ</h2>");

// Menu
out.println("<nav>");
out.println("<a id='addButton' href='#' onclick='showAddForm()'>Thêm Mới</a>");
out.println("<a id='searchButton' href='#' onclick='showSearchForm()'>Tìm Kiếm</a>");  
out.println("</nav>");
out.println("<body>");

out.println("<div id='searchForm' style='display:none;'>");
out.println("<h2>Tìm Kiếm Địa Chỉ</h2>");
out.println("<form method='get'>");
out.println(" <label for='searchma_dia_chi'>Mã Địa Chỉ:</label>");
out.println(" <input type='text' id='searchma_dia_chi' name='searchma_dia_chi'>");
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
out.println("<h3>Thêm mới địa chỉ</h3>");
out.println("<form method='post' action='AddDiaChiServlet'>");
out.println("Mã địa chỉ: <input type='text' name='ma_dia_chi'><br>");
out.println("Số nhà: <input type='text' name='so_nha'><br>");
out.println("Đường: <input type='text' name='duong'><br>");
out.println("Phường: <input type='text' name='phuong'><br>");
out.println("Quận: <input type='text' name='quan'><br>");
out.println("Tỉnh thành phố: <input type='text' name='tinh_thanh_pho'><br>");
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
                if (tableName == null || tableName.isEmpty() || tableName.equals("dia_chi")) {
                    // Hiển thị thông tin hộ khẩu
                    stmt = con.createStatement();
                     String sql1 = "SELECT * FROM dia_chi";
        String sql2 = "SELECT * FROM dia_chi WHERE ma_dia_chi LIKE '%" + searchMDC + "%'";
        
        // Use sql2 if searchMQH is not null and not empty
        ResultSet rs;
        if (searchMDC != null && !searchMDC.isEmpty()) {
            rs = stmt.executeQuery(sql2);
        } else {
            rs = stmt.executeQuery(sql1);
        }

                    out.println("<table border=1 width=50% height=50%>");
                    out.println("<tr><th>Mã Địa Chỉ</th><th>Số Nhà</th><th>Đường</th><th>Phường</th><th>Quận</th><th>Tỉnh Thành Phố</th></tr>");

                    while (rs.next()) {
                        String MDC = rs.getString("ma_dia_chi");
                        String SN = rs.getString("so_nha");
                        String D = rs.getString("duong");
                        String P = rs.getString("phuong");
                        String Q = rs.getString("quan");
                        String TTP = rs.getString("tinh_thanh_pho");

                        out.println("<tr><td>" + MDC + "</td><td>" + SN + "</td><td>" + D + "</td><td>" + P
            + "</td><td>" + Q + "</td><td>" + TTP + "</td><td><a href='EditDiaChiRecordServlet?ma_dia_chi=" + MDC + "'>Edit</a></td>"
            + "<td><a href='DeleteDiaChiRecordServlet?ma_dia_chi=" + MDC + "'>Delete</a></td></tr>");
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
