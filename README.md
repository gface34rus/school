# School Project

## Описание
Демонстрационный проект на Spring Boot, который управляет студентами, факультетами и аватарами.

## Стек технологий
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Liquibase
- Swagger для документации API

## Установка
1. Клонируйте репозиторий.
2. Убедитесь, что у вас установлен Maven.
3. Настройте базу данных PostgreSQL.
4. Запустите команду:
   ```bash
   mvn spring-boot:run
API
Аватары
POST /avatar/upload: загрузка аватара.
GET /avatar/get/from-db: получение аватара из базы данных.
GET /avatar/get/all: получение всех аватаров.
Факультеты
POST /faculty/add: добавление факультета.
GET /faculty/{id}: получение факультета по ID.
DELETE /faculty/{id}: удаление факультета по ID.
Студенты
POST /student: добавление студента.
GET /student/{id}: получение студента по ID.
GET /student/find-all: получение всех студентов.
