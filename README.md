# JobHunter - RESTful API with Java Spring

**JobHunter** là một ứng dụng web backend cung cấp các dịch vụ API cho việc tìm kiếm và quản lý công việc. Dự án này được xây dựng bằng **Java Spring Boot**, với các tính năng xác thực và phân quyền người dùng thông qua **JWT** và **OAuth2**, và kết nối cơ sở dữ liệu **MySQL** thông qua **Spring JPA**.

## Công nghệ sử dụng

### Backend

- **Spring Boot**: Framework phát triển ứng dụng Java, giúp cấu hình và triển khai nhanh chóng các API RESTful.
- **Spring Security**: Xác thực và phân quyền người dùng với **JWT** và **OAuth2** mà không cần viết custom filter.
- **Spring JPA**: Thao tác với cơ sở dữ liệu MySQL thông qua ORM
- **Thymeleaf**: Hỗ trợ các template để sử dụng trong gửi mail
- **Spring filters**: Hỗ trợ các tính năng như tìm kiếm, phân trang và sắp xếp

### Database

- **MySQL**: Cơ sở dữ liệu được sử dụng để lưu trữ thông tin công việc và người dùng.

### Build tools

- **Gradle - Kotlin**: Công cụ build và quản lý phụ thuộc (dependencies) của dự án.

### Language

- **Java**: Dự án sử dụng **Java** làm ngôn ngữ lập trình chính cho backend.

## Các tính năng chính

- Đăng ký và đăng nhập người dùng với JWT.
- Xem danh sách công việc và chi tiết công việc.
- Tìm kiếm công việc theo từ khóa và địa điểm.
- Quản lý công việc và ứng tuyển vào các công việc phù hợp.
- Gửi mail xác nhận ứng tuyển (sử dụng thymeleaf)
- Nộp Đơn ứng tuyển vào vị trí công việc
- Bảo mật API với JWT và OAuth2.
- Phân quyền ngươi dùng (USER, ADMIN, HR)

## Cấu trúc dự án

### Backend (Java Spring)

- **main/java**: Chứa các lớp Java, bao gồm Controller, Service, Repository.

  - **controller**: Các lớp điều khiển xử lý yêu cầu HTTP.
  - **service**: Các lớp chứa logic nghiệp vụ.
  - **repository**: Các lớp quản lý truy cập dữ liệu từ cơ sở dữ liệu.

- **src/main/resources**: Chứa các tệp cấu hình và tài nguyên tĩnh như tệp cấu hình Spring, các template (nếu có).

- **application.properties**: Cấu hình các tham số kết nối cơ sở dữ liệu, thông tin bảo mật, v.v.

## Yêu cầu hệ thống

- **Java Version**: Java 17.
- **Build tools**: Gradle.
- **Database**: MySQL.
- **IDE**: IntelliJ IDEA, Eclipse, Spring Tool Suite, v.v.
