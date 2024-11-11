CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    CONSTRAINT chk_name_length CHECK (LENGTH(name) BETWEEN 2 AND 250),
    CONSTRAINT chk_email_length CHECK (LENGTH(email) BETWEEN 6 AND 254)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT chk_category_name_length CHECK (LENGTH(name) BETWEEN 1 AND 50)
);

CREATE TABLE IF NOT EXISTS events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    annotation VARCHAR(2000) NOT NULL,
    category_id BIGINT NOT NULL,
    confirmed_requests BIGINT,
    created_on TIMESTAMP NOT NULL,
    description VARCHAR(7000) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    initiator_id BIGINT NOT NULL,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL,
    paid BOOLEAN NOT NULL,
    participant_limit INT NOT NULL,
    published_on TIMESTAMP,
    request_moderation BOOLEAN NOT NULL,
    state VARCHAR(50) NOT NULL,
    title VARCHAR(120) NOT NULL,
    CONSTRAINT chk_annotation_length CHECK (LENGTH(annotation) BETWEEN 20 AND 2000),
    CONSTRAINT chk_description_length CHECK (LENGTH(description) BETWEEN 20 AND 7000),
    CONSTRAINT chk_title_length CHECK (LENGTH(title) BETWEEN 3 AND 120),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT fk_initiator FOREIGN KEY (initiator_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pinned BOOLEAN NOT NULL,
    title VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT chk_compilation_title_length CHECK (LENGTH(title) BETWEEN 1 AND 50)
);

CREATE TABLE IF NOT EXISTS compilation_events (
    compilation_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    CONSTRAINT fk_compilation FOREIGN KEY (compilation_id) REFERENCES compilations(id),
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES events(id),
    CONSTRAINT pk_compilation_event PRIMARY KEY (compilation_id, event_id)
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id BIGINT NOT NULL,
    requester_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created TIMESTAMP NOT NULL,
    CONSTRAINT fk_event_request FOREIGN KEY (event_id) REFERENCES events(id),
    CONSTRAINT fk_requester FOREIGN KEY (requester_id) REFERENCES users(id)
);
