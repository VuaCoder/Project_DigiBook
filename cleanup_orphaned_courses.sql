-- Script để cleanup các course orphaned (không có document nào)
-- Chạy script này để xóa các course không còn được sử dụng

USE DIGIBOOK;

-- Xem các course orphaned trước khi xóa
SELECT 
    c.id,
    c.title,
    c.department,
    c.author,
    c.publication_year,
    (SELECT COUNT(*) FROM Document d WHERE d.course_id = c.id) as document_count
FROM Course c
HAVING document_count = 0;

-- Xóa các course orphaned
DELETE c FROM Course c
WHERE NOT EXISTS (
    SELECT 1 FROM Document d WHERE d.course_id = c.id
);

-- Kiểm tra kết quả sau khi xóa
SELECT 
    c.id,
    c.title,
    c.department,
    c.author,
    c.publication_year,
    (SELECT COUNT(*) FROM Document d WHERE d.course_id = c.id) as document_count
FROM Course c
ORDER BY c.created_at DESC;
