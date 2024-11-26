CREATE database warehouse_program;
use warehouse_program;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       Role VARCHAR(20) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE line (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       num INT NOT NULL,
                       description TEXT,
                       user_id INT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE parts (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       description TEXT,
                       price DECIMAL(10, 2) CHECK (price > 0),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE warehouse (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(50) UNIQUE NOT NULL
);


CREATE TABLE warehouse_parts (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 warehouse_id INT NOT NULL,
                                 part_id INT NOT NULL,
                                 quantity INT NOT NULL CHECK (quantity >= 0),
                                 FOREIGN KEY (warehouse_id) REFERENCES warehouse(id) ON DELETE CASCADE,
                                 FOREIGN KEY (part_id) REFERENCES parts(id) ON DELETE CASCADE,
                                 UNIQUE (warehouse_id, part_id)
);

CREATE TABLE lineParts (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           line_id INT NOT NULL,
                           part_id INT NOT NULL,
                           quantity INT NOT NULL CHECK (quantity > 0),
                           FOREIGN KEY (line_id) REFERENCES line(id) ON DELETE CASCADE,
                           FOREIGN KEY (part_id) REFERENCES parts(id) ON DELETE CASCADE
);

CREATE TABLE line_logs (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      user_id INT NOT NULL,
                      line_id INT NOT NULL,
                      part_id INT NOT NULL,
                      action ENUM('ADD', 'REMOVE') NOT NULL,
                      quantity INT CHECK (quantity > 0),
                      timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                      FOREIGN KEY (line_id) REFERENCES line(id) ON DELETE CASCADE,
                      FOREIGN KEY (part_id) REFERENCES parts(id) ON DELETE CASCADE
);

CREATE TABLE warehouse_logs (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                user_id INT NOT NULL,
                                warehouse_id INT NOT NULL,
                                part_id INT NOT NULL,
                                action ENUM('ADD', 'REMOVE') NOT NULL,
                                quantity INT NOT NULL CHECK (quantity > 0),
                                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                FOREIGN KEY (warehouse_id) REFERENCES warehouse(id) ON DELETE CASCADE,
                                FOREIGN KEY (part_id) REFERENCES parts(id) ON DELETE CASCADE
);