# Используем официальный образ OpenJDK для сборки
FROM openjdk:21

# Указываем рабочую директорию внутри контейнера
WORKDIR /

RUN mkdir -p $HOME/maven3 && cd $HOME/maven3
FROM maven:3.9.8 AS maven
ENV M2_HOME=$HOME/maven3

COPY  . /
RUN mvn clean formatter:format formatter:validate install
RUN mv ./target/data-simulator-0.0.1-SNAPSHOT.jar app.jar

# Копируем файл jar из локальной сборки в контейнер
#COPY target/data-simulator-0.0.1-SNAPSHOT.jar app.jar

# Указываем команду запуска приложения
CMD ["java", "-jar", "app.jar"]

# Указываем порт, который будет слушать приложение
EXPOSE 9091
