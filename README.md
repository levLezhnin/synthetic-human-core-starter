# Synthetic human core starter

## Установка

Проект использует Kafka (вместе с zookeeper и kafka-ui), Prometheus и Grafana. Всё это поднимается на докере:
- `docker-compose.yaml` находится по пути: ```scripts/docker-compose.yaml```;
- `Dockerfile` располагается по пути: `scripts/config/grafana/Dockerfile`.

## Запуск
- Файл запуска: `App.java`;
- Сбор стартера происходит командой: `mvn clean install`;

## Структура

1) Все файлы, связанные с приёмом и исполнением команд андроидом, располагаются по пути: `src/main/java/org/example/command_handler`;
2) Файлы, связанные с аудитом действий андроида, располагаются по пути: `src/main/java/org/example/command_handler/audit`;
3) Процесс, отправдяющий команды в обработку, описан в файле: `src/main/java/org/example/command_handler/CommandExecutor.java`;
4) Файлы, связанные с сбором метрик, располагаются по пути: `src/main/java/org/example/command_handler/metrics`;
5) Файлы, связанные с отправкой сообщений по Kafka, располагаются по пути: `src/main/java/org/example/messaging`;
6) Модуль обработки ошибок располагается по пути: `src/main/java/org/example/error_handler`;

## Зависимости
- Java 17+
- Maven
- Spring 2.7.0