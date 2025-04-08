# 📱 MobileShop - Website bán điện thoại

## 🧾 Giới thiệu

**MobileShop** là một trang web thương mại điện tử cho phép người dùng mua điện thoại trực tuyến.  
Dự án được xây dựng bằng **JSP/Servlet**, sử dụng **NetBeans** và chạy trên **Apache Tomcat**.  
Hệ thống hỗ trợ đăng nhập bằng **Google hoặc tài khoản riêng**, thanh toán qua **VNPAY**, và gửi **email thông báo** khi mua hàng thành công. Ngoài ra, tích hợp API **địa chỉ tỉnh/thành** để người dùng chọn địa chỉ giao hàng một cách thuận tiện.

---

## 🌟 Chức năng chính

### 🛒 1. Trang mua điện thoại
- Hiển thị danh sách các điện thoại đang kinh doanh
- Bộ lọc theo hãng, giá, loại điện thoại
- Xem chi tiết sản phẩm

### 🔐 2. Đăng nhập và đăng ký
- Đăng nhập bằng **Google**
- Đăng nhập/đăng ký bằng tài khoản riêng

### 👤 3. Quản lý hồ sơ người dùng
- Chỉnh sửa thông tin cá nhân
- Xem lịch sử mua hàng

### 🧺 4. Giỏ hàng
- **Lưu trữ giỏ hàng bằng cookie** kể cả khi chưa đăng nhập
- Thêm, xoá, cập nhật số lượng sản phẩm trong giỏ

### 💳 5. Thanh toán
- Thanh toán trực tuyến bằng **VNPAY**
- Nhận email xác nhận đơn hàng sau khi thanh toán

### 📨 6. Tích hợp Email
- Gửi email thông báo khi mua hàng thành công

### 📍 7. API địa chỉ tỉnh/thành
- Chọn địa chỉ giao hàng qua dropdown: **Tỉnh/Thành → Quận/Huyện → Phường/Xã**

---

## ⚙️ Công nghệ sử dụng

- Java Servlet & JSP
- Apache Tomcat
- NetBeans IDE
- JSTL, CSS, JavaScript
- Google OAuth 2.0
- VNPAY Payment Gateway
- JavaMail API
- REST API địa chỉ (tỉnh/thành Việt Nam)

---

## 🚀 Khởi chạy dự án

1. Mở dự án bằng **NetBeans**
2. Cấu hình cơ sở dữ liệu trong `DBUtils.java`
3. Cài đặt Tomcat và chạy project
4. Truy cập `http://localhost:8080/MobileShop`

---

## ✅ Ghi chú

- Dữ liệu tỉnh/thành có thể sử dụng từ API công khai như [API Việt Nam - Province](https://provinces.open-api.vn/)
- Google OAuth cần cấu hình Client ID/Secret
- VNPAY yêu cầu tài khoản thử nghiệm và cấu hình callback URL đúng

