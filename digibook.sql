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
(N'Giáo trình tiếng Nhật N5 - Chương 10', 
 N'https://example.com/cover_image.jpg', -- bạn có thể để NULL hoặc link ảnh bìa
 N'Ngôn ngữ Nhật', 
 N'DungMori', 
 2020, 
 N'Giáo trình N5 - Chương 10 từ DungMori');
INSERT INTO Document (course_id, file_path)
VALUES (SCOPE_IDENTITY(), N'D:\fifa\CHUYÊN NGÀNH 3\JPD113\DungMori\N5\Chương 10.pdf');
ALTER TABLE Document ADD owner_id INT NULL;
ALTER TABLE Document
  ADD CONSTRAINT FK_Document_Owner
  FOREIGN KEY (owner_id) REFERENCES UserAccount(id);
