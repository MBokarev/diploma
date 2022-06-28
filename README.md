# Дипломный проект по профессии "Тестировщик".

### Предусловия для работы с проектом ###

***На ПК должны быть установлены:***

* *IntelliJ IDEA Community Edition 2021.3.1*
* *Java 11*
* *Docker*
* *Git*

*Создать клон проекта в рабочую папку на ПК с помощью команды* `git clone https://github.com/MBokarev/diploma.git`

### Запуск приложения ###

* *Запустить Docker*

* *Открыть проект в IntelliJ IDEA Community Edition*

* *В консоли IDEA выполнить команду `docker-compose up`*

***Для запуска приложения на MySQL***

* *В новой вкладке консоли выполнить команду `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`*

* *В новой вкладке консоли выполнить команду для запуска тестов `./gradlew test "-Ddb.url=jdbc:mysql://localhost:3306/app"`*

***Для запуска приложения на PostgreSQL***

* *В новой вкладке консоли выполнить команду `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`*

* *В новой вкладке консоли выполнить команду для запуска тестов `./gradlew test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`*

***Для просмотра отчёта по тестам в браузере***

* *Выполнить в консоли команду `./gradlew allureServe`*

***Для завершения работы плагина отчётности***

* *Воспользоваться сочетанием клавиш `Ctrl+C`*
