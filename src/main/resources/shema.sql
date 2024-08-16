CREATE TABLE Entreprise (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    license_id INT NOT NULL,
    FOREIGN KEY (license_id) REFERENCES License(id)
);

CREATE TABLE Departement (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    entreprise_id INT,
    FOREIGN KEY (entreprise_id) REFERENCES Entreprise(id)
);

CREATE TABLE License (
    id INT PRIMARY KEY AUTO_INCREMENT,
    startDate DATE,
    endDate DATE,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE Users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    role VARCHAR(50) NOT NULL,
    address VARCHAR(250) NOT NULL,
    picture VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    in_Conger BOOLEAN,
    departement_id INT,
    entreprise_id INT,
    FOREIGN KEY (departement_id) REFERENCES Departement(id),
    FOREIGN KEY (entreprise_id) REFERENCES Entreprise(id)
);

CREATE TABLE Absences (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    type VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES Users(id),
);

CREATE TABLE Superuser(
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(250) NOT NULL
)


INSERT INTO License (startDate, endDate, status)
VALUES ('2024-01-01', '2025-01-01', 'active');
INSERT INTO Entreprise (name, license_id)
VALUES ('Tech Solutions', 1);
INSERT INTO Departement (name, entreprise_id)
VALUES ('Développement', 1);







