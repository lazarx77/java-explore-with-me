CREATE TABLE stats (
    id BIGINT PRIMARY KEY, -- Идентификатор записи
    app VARCHAR(255) NOT NULL, -- Название сервиса, из которого был осуществлен запрос
    uri VARCHAR(255) NOT NULL, -- URI для которого был осуществлен запрос
    ip VARCHAR(45) NOT NULL, -- IP-адрес пользователя
    timestamp DATETIME NOT NULL -- Дата и время запроса
);