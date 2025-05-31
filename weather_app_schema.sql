-- Create database
CREATE DATABASE IF NOT EXISTS weather_app;
USE weather_app;


-- Table for weather search history
CREATE TABLE IF NOT EXISTS weather_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    temperature FLOAT NOT NULL,
    `condition` VARCHAR(100) NOT NULL,
    search_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
