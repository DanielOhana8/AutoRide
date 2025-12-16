CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
	location_x INT NOT NULL,
    location_y INT NOT NULL,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 100.0
);

CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    model VARCHAR(100) NOT NULL,
    location_x INT NOT NULL,
    location_y INT NOT NULL,
    available BOOLEAN NOT NULL
);

CREATE TABLE rides (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    car_id INT NOT NULL REFERENCES cars(id),
    start_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP,
	start_location_x INT NOT NULL,
	start_location_y INT NOT NULL,
	end_location_x INT,
	end_location_y INT,
    total_cost DECIMAL(10, 2) NOT NULL DEFAULT 0.0
);

INSERT INTO cars (model, location_x, location_y, available) VALUES 
('Toyota Corolla', 10, 10, TRUE),
('Mazda 3', 50, 60, TRUE),
('Hyundai Ioniq', 20, 80, TRUE),
('Tesla Model 3', 5, 5, TRUE);
