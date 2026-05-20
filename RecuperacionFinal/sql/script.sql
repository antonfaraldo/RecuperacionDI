CREATE DATABASE IF NOT EXISTS car_management_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE car_management_db;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,          -- UNIQUE: No puede haber dos usuarios iguales
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,                -- Espacio amplio para guardar la contraseña codificada
    role VARCHAR(20) DEFAULT 'user'                -- Por defecto tendrá el rol 'user'
);

INSERT INTO users (username, email, password, role) VALUES 
('admin', 'admin@gestorcoches.com', 'admin123', 'admin');

CREATE TABLE IF NOT EXISTS cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    horse_power INT NOT NULL,
    type VARCHAR(50) NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    image_url VARCHAR(255) DEFAULT '',
    is_favorite TINYINT(1) DEFAULT 0
);

-- Registros de prueba para los coches
INSERT INTO cars (brand, model, horse_power, type, is_favorite) VALUES 
('Toyota', 'Yaris', 116, 'Compact', 0),
('Ford', 'Mustang', 450, 'Sports', 1),
('Audi', 'A4', 204, 'Sedan', 0);

CREATE TABLE IF NOT EXISTS user_favorites (
    user_id INT PRIMARY KEY,                       -- Clave primaria: Asegura un ÚNICO registro (un único favorito) por usuario
    car_id INT NOT NULL,                           -- ID del coche marcado como favorito
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE
);