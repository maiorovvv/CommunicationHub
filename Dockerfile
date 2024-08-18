# Используйте образ с Maven и OpenJDK
FROM maven:3.8.7-openjdk-18-slim

# Установите рабочую директорию
WORKDIR /app

# Копируйте файл pom.xml и исходный код
COPY pom.xml .
COPY src ./src

# Соберите проект
RUN mvn package

# Установите порт, который будет слушать ваше приложение
EXPOSE 8080

# Определите команду для запуска приложения
ENTRYPOINT ["java", "-jar", "target/communication-hub.jar"]
