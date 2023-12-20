<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            text-align: center;
        }
        #image-container {
            margin-top: 20px; /* Adjust as needed */
        }
        img {
            width: 100%; /* Make the image take 100% of the container width */
            max-width: 600px; /* Set a maximum width for the image */
            display: block;
            margin: 0 auto; /* Center the image */
            border-radius: 10px; /* Optional: Add border radius for a rounded appearance */
        }
    </style>
</head>
<body>
    <h2 style='color: #fff; background: linear-gradient(to right, #4CAF50, #336699); padding: 15px; border-radius: 8px; border: 2px solid #336699; text-align: center;'>QUẢN LÝ NHÂN KHẨU</h2>

    <div style='display: flex; justify-content: space-around; background-color: #f1f1f1; padding: 10px;'>
        <!-- ... (your existing navigation links) ... -->
    </div>

    <!-- Add the image container with centered and larger image -->
    <div id="image-container">
        <img src='https://tse2.mm.bing.net/th?id=OIP.z-A3oVueR9k-b2tugS55ogHaFj&pid=Api&P=0&h=180' alt='Your Image Alt Text'>
    </div>

    <!-- Add the login form container -->
    <div class="form-container">
        <form action="user" method="get">
            <h2>Form Đăng Nhập</h2>
            <label for="username">Gmail:</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Mật khẩu:</label>
            <input type="password" id="password" name="password" required>

            <input type="submit" value="Đăng nhập">
        </form>

        <form action="user" method="post">
            <input type="submit" value="Đăng kí">
        </form>

        <form action="qmk" method="get">
            <input type="submit" value="Quên mật khẩu">
        </form>
    </div>
</body>
</html>
