create database DIGIBOOK;
use DIGIBOOK;

--USER
CREATE TABLE UserAccount (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT GETDATE()
);
-- COURSE
CREATE TABLE Course (
    id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,        -- Tên giáo trình
    cover_image NVARCHAR(255),           -- Link hoặc đường dẫn hình bìa sách
    department NVARCHAR(100),            -- Khoa/Ngành
    author NVARCHAR(100),                -- Tác giả
    publication_year INT,                -- Năm xuất bản
    description NVARCHAR(MAX),           -- Mô tả thêm nếu cần
    created_at DATETIME DEFAULT GETDATE(),
);
-- Lưu file PDF
CREATE TABLE Document (
    id INT IDENTITY(1,1) PRIMARY KEY,
    course_id INT FOREIGN KEY REFERENCES Course(id),
    file_path NVARCHAR(255) NOT NULL,    -- đường dẫn file PDF trên server
    uploaded_at DATETIME DEFAULT GETDATE()
);

-- Lưu note và highlight
CREATE TABLE Annotation (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT FOREIGN KEY REFERENCES UserAccount(id),
    document_id INT FOREIGN KEY REFERENCES Document(id),
    page_number INT,                     -- trang được note
    highlight_text NVARCHAR(MAX),        -- đoạn text highlight
    note_text NVARCHAR(MAX),             -- note thêm của user
    created_at DATETIME DEFAULT GETDATE()
);
-- VOCABULARY
CREATE TABLE Vocabulary (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT FOREIGN KEY REFERENCES UserAccount(id),
    document_id INT FOREIGN KEY REFERENCES Document(id),
    page_number INT,                     -- trang chứa từ vựng
    word NVARCHAR(255) NOT NULL,         -- từ vựng
    meaning NVARCHAR(255),               -- nghĩa của từ
    context_text NVARCHAR(MAX),          -- đoạn văn bản chứa từ
    created_at DATETIME DEFAULT GETDATE()
);
select * from UserAccount
INSERT INTO Course (title, cover_image, department, author, publication_year, description)
VALUES 
(N'Nhập môn kế toán', 
 N'https://scontent.fdad3-4.fna.fbcdn.net/v/t39.30808-6/489325145_1287426639477884_4070292984145116609_n.jpg?stp=cp6_dst-jpg_p526x296_tt6&_nc_cat=101&cb=99be929b-7bdcbe47&ccb=1-7&_nc_sid=127cfc&_nc_ohc=Ff7vBeZc344Q7kNvwEW7AlK&_nc_oc=AdlJwfhRhD6eRBvO7nC_jMABcSWA7n8RT6BGTlJ1_-UJa8wQyG7s6FskYod3YtbtAwM&_nc_zt=23&_nc_ht=scontent.fdad3-4.fna&_nc_gid=ID2lWKHpHx7tqYJCcyz2Eg&oh=00_AfXi7fVnCdDEmKjYdqy5G-Jp9UaGocyH6cVMwUkNYwcElg&oe=68ACD027', -- bạn có thể để NULL hoặc link ảnh bìa
 N'Kế toán', 
 N'TS. Nguyễn Hữu Cường chủ biên; PGS.TS. Ngô Hà Tấn, TS. Phan Thị Đỗ Quyên.', 
 2023, 
 N'Giáo trình N5 - Chương 10 từ DungMori');
INSERT INTO Document (course_id, file_path)
VALUES (SCOPE_IDENTITY(), N'D:\fifa\CHUYÊN NGÀNH 3\JPD113\DungMori\N5\Chương 10.pdf');
ALTER TABLE Document ADD owner_id INT NULL;
ALTER TABLE Document
ADD CONSTRAINT FK_Document_Owner
FOREIGN KEY (owner_id) REFERENCES UserAccount(id);
-- Add giá trị
INSERT INTO Course (title)
VALUES 
(N'CSDL'),
(N'CTDLVGT'),
(N'HTTTKT'),
(N'KDQT'),
(N'KDTMQT'),
(N'KNS'),
(N'KTDT'),
(N'KTHD'),
(N'KTL'),
(N'KTVM'),
(N'NMDLLNC'),
(N'NMKT'),
(N'QTCL'),
(N'QTH'),
(N'QTKDQT');
