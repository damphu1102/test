🧰 Công nghệ sử dụng

* Ngôn ngữ: Java 11
* Framework: Spring Boot 2.7.15
* Build Tool: Gradle
* Cơ sở dữ liệu: MySQL
* Bảo mật: Spring Security, JWT (JSON Web Token)
* ORM: Spring Data JPA / Hibernate
* Thư viện hỗ trợ: Lombok, JWT

✨ Các tính năng chính

* Quản lý thông tin người dùng:** Lưu trữ các thông tin cơ bản như `name`, `username`, `password`, `email`, `phone`, `role`, và `status`.
* Soft Delete: Người dùng không bị xóa vĩnh viễn khỏi cơ sở dữ liệu.
  Thay vào đó, họ được đánh dấu là `Inactive` bằng cách cập nhật trường `status` trong entity `User`.
  Các truy vấn đọc danh sách người dùng mặc định chỉ trả về các người dùng đang `Active`.
* Authentication (Xác thực): Cho phép người dùng đăng ký tài khoản và đăng nhập để nhận JWT, dùng cho việc truy cập các tài nguyên được bảo vệ.
* Authorization (Phân quyền): Các API được bảo vệ yêu cầu một JWT hợp lệ để truy cập. Có thể mở rộng để phân quyền theo vai trò (roles) cụ thể.
* RESTful APIs: Cung cấp các endpoint rõ ràng, tuân thủ nguyên tắc REST cho các thao tác CRUD.
* Mã hóa mật khẩu: Sử dụng BCryptPasswordEncoder để mã hóa mật khẩu người dùng, tăng cường bảo mật.

📁 Cấu trúc thư mục dự án
Dự án được tổ chức theo cấu trúc sau:
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── test/
│   │               ├── config/        # Cấu hình ứng dụng (Security, JWT, CORS)
│   │               ├── controller/    # Các REST API Endpoints
│   │               ├── dto/           # Data Transfer Objects (Dùng cho input/output của API)
│   │               ├── entity/        # Các JPA Entities ánh xạ tới bảng trong DB (User, Status)
│   │               ├── repository/    # Spring Data JPA Repositories để tương tác với DB
│   │               ├── request/       # DTOs cho các yêu cầu API (LoginRequest, RegisterRequest)
│   │               ├── service/       # Logic nghiệp vụ (Interfaces và Implementations)
│   │               ├── utils/         # Các lớp tiện ích chung (ví dụ: liên quan đến JWT)
│   │               └── TestApplication.java # Lớp khởi chạy ứng dụng Spring Boot
│   └── resources/
│       └── application.properties     # Cấu hình ứng dụng và cơ sở dữ liệu
└── test/
└── java/
└── com/
└── example/
└── test/              # Các bài kiểm thử đơn vị và tích hợp

🚀 Cài đặt môi trường
Trước khi chạy ứng dụng, bạn cần cài đặt các công cụ sau trên hệ thống của mình:
1.  Java Development Kit (JDK) 11:
    * Tải xuống và cài đặt JDK 11 từ trang web của Oracle (cần tài khoản Oracle) hoặc sử dụng một bản phân phối OpenJDK mã nguồn mở (ví dụ: AdoptOpenJDK, Azul Zulu, Eclipse Temurin).
    * Sau khi cài đặt, bạn cần thiết lập biến môi trường `JAVA_HOME` trỏ đến thư mục cài đặt JDK của bạn.
      Ví dụ: `C:\Program Files\Java\jdk-11` trên Windows hoặc `/usr/lib/jvm/java-11-openjdk-amd64` trên Linux.
    * Thêm `%JAVA_HOME%\bin` (Windows) hoặc `$JAVA_HOME/bin` (Linux/macOS) vào biến môi trường `PATH` của hệ thống để có thể chạy các lệnh `java` và `javac` từ bất kỳ đâu.
    * Để kiểm tra cài đặt, mở terminal/command prompt và chạy các lệnh sau:
        ```bash
        java -version
        javac -version
        ```
        Đảm bảo đầu ra hiển thị phiên bản 11.x.x.
2.  Gradle:
    * Dự án này đã được cấu hình với **Gradle Wrapper** (`./gradlew` trên Linux/macOS hoặc `gradlew.bat` trên Windows).
    * Điều này có nghĩa là bạn không cần phải cài đặt Gradle toàn cầu trên hệ thống của mình.
    * Wrapper sẽ tự động tải xuống phiên bản Gradle cần thiết khi bạn chạy các lệnh Gradle lần đầu tiên.
3.  MySQL Server:
    * Bạn cần cài đặt và chạy một instance **MySQL Server** trên máy tính của mình.
    * Bạn có thể tải xuống trình cài đặt MySQL Community Server từ trang web chính thức của MySQL: [https://dev.mysql.com/downloads/mysql/](https://dev.mysql.com/downloads/mysql/)
    * Trong quá trình cài đặt, hãy ghi nhớ **mật khẩu cho người dùng `root`** hoặc tạo một người dùng MySQL mới với quyền truy cập phù hợp.
    * Đảm bảo MySQL server đang chạy trước khi bạn khởi động ứng dụng Spring Boot.
    * Cấu hình kết nối Database:
        Mở file `src/main/resources/application.properties` trong dự án của bạn.
        Cập nhật các dòng sau với thông tin kết nối MySQL của bạn (đảm bảo `your_root_password` khớp với mật khẩu MySQL của bạn):
        ```properties
        # Database Configuration
        spring.datasource.url=jdbc:mysql://localhost:3306/user_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
        spring.datasource.username=root
        spring.datasource.password=your_root_password
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

        # JPA Configuration
        spring.jpa.hibernate.ddl-auto=update # Hibernate sẽ tự động tạo/cập nhật schema database dựa trên các entity.
        Trong môi trường Production, bạn nên cân nhắc sử dụng 'validate' hoặc 'none' và dùng các công cụ migration như Flyway/Liquibase.
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
        ```
        *Lưu ý:* `createDatabaseIfNotExist=true` trong `spring.datasource.url` sẽ tự động tạo database `user_db` nếu nó chưa tồn tại khi ứng dụng khởi động lần đầu.
