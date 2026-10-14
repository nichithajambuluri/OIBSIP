CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS trains (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    train_number INTEGER NOT NULL UNIQUE,
    train_name TEXT NOT NULL,
    source TEXT NOT NULL,
    destination TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS reservations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    pnr TEXT NOT NULL UNIQUE,
    passenger_name TEXT NOT NULL,
    train_number INTEGER NOT NULL,
    train_name TEXT NOT NULL,
    class_type TEXT NOT NULL,
    journey_date TEXT NOT NULL,
    source TEXT NOT NULL,
    destination TEXT NOT NULL,
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (train_number) REFERENCES trains(train_number)
);
