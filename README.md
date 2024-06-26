# Добро пожаловать в управление вкладами!
Данное приложение было написано в следующем стеке технологий:
1. Фреймворк для создания веб-приложений с использованием Java: [Spring Boot](https://spring.io/projects/spring-boot).
2. Свободная реляционная система управления базами данных: [MySQL](https://www.mysql.com/).
3. Фреймворк для языка программирования Java, предназначенный для автоматического тестирования программ: [JUnit](https://junit.org/junit5/docs/current/user-guide/)

# Инструкция по настройке.
Для того, чтобы использовать приложение, необходимо настроить локальный хостинг MySQL, к примеру, используя СУБД [phpMyAdmin](https://www.phpmyadmin.net/). В своём проекте я использовал сборку веб-сервера на основе Apache: [Wamp Server](https://www.wampserver.com/). После запуска сборки веб-сервера, по умолчанию MySQL находится на порту 3306.
Импортируйте дамп базы данных $${\color{lightblue}\stroki\src\main\resources\}$$ $${\color{blue}greendata.sql}$$
Откройте файл $${\color{lightblue}\stroki\src\main\resources\}$$ $${\color{blue}application.properties}$$, чтобы изменить некоторые строки для доступа к базе данных. Параметры, которые можно изменить:
- Параметр для указания пути к базе данных: $${\color{green}spring.datasource.url=}$$ Протоколы $${\color{lightgreen}jdbc:mysql://}$$ оставьте по умолчанию. Поменяйте домен $${\color{lightgreen}localhost:3306/}$$, если адрес порта отличается от вашего - замените на свои цифры, если же база данных находится на сервере - то необходимо указать путь до неё. Наконец, замените путь к странице $${\color{lightgreen}stroki}$$, указав существующую базу данных на вашем хостинге/сервере. Затем импортируйте структуру таблиц из файла $${\color{lightblue}\stroki\src\main\resources\}$$ $${\color{blue}stroki.sql}$$ в вашей СУБД.
- Параметр указания имени пользователя для доступа к базе данных: $${\color{green}spring.datasource.username=}$$ Укажите вашего пользователя после знака "=".
- Параметр указания пароля для доступа к базе данных для того пользователя, что был указан выше: $${\color{green}spring.datasource.password=}$$
- Остальные параметры изменений не требуют.
После настройки приложения можно запускать проект в своей IDE, а затем перейти [по ссылке для доступа к главной странице](http://localhost:8080/).

# Примечание.
[На стартовой странице](http://localhost:8080/) отсутствует стилизация, но присутствует функционал серверной части приложения. На данной странице можно тестировать взаимодействие с системой. Стили будут добавлены позже.
На этой же странице есть списки, которые не подгружаются с сервера. У автора была идея подгрузить списки с помощью PHP, но в Community-версии Intellij IDEa отсуствует поддержка PHP-скриптов.

# Инструкция по эксплуатации (CRUD).
## CREATE
Позволяет создавать клиентов банка, банки и вклады.
- **"Клиент: "**) уникальные поля *"name"* и *"shortName"*.
- **"Банк: "**) уникальные поля *"name"* и *"bic"*.
- **"Вклады: "**) уникальных полей нет.
## READ
Позволяет получать список существующих клиентов, банков и вкладов с выбором порядка: по умолчанию (*"none"*), по возрастанию (*"asc"*), по убыванию (*"desc"*).
- **"Клиент: "**) поиск всех клиентов (список), поиск по полям *"name"* и *"shortName"* одновременно (ключ-значение), по полю *"organization_form"* (список).
- **"Банк: "**) поиск всех банков с указанием поля сортировки (список), поиск по полям *"name"* и *"bic"* (ключ-значение).
- **"Вклады: "**) поиск всех вкладов, поиск по полям *"customer"*, *"bank"*, *"opening"*, *"percentage"* и *"duration"* (списки).
## UPDATE
Позволяет редактировать существующих клиентов, банков и вкладов.
- **"Клиент: "**) редактирование через поиск по полям *"name"* и *"shortName"* одновременно (ключ-значение).
- **"Банк: "**) редактирование через поиск по полям *"name"* и *"bic"* одновременно (ключ-значение).
- **"Вклады: "**) редактирование через поиск по всем полям: *"customer"*, *"bank"*, *"percentage"* и *"duration"* (списки).
## DELETE
Позволяет удалять существующих клиентов, банков и вкладов.
- **"Клиент: "**) удаление через поиск по полям *"name"* и *"shortName"* одновременно (ключ-значение).
- **"Банк: "**) удаление через поиск по полям *"name"* и *"bic"* одновременно (ключ-значение).
- **"Вклады: "**) удаление через поиск по всем полям: *"customer"*, *"bank"*, *"percentage"* и *"duration"* (списки).
## UNIT-тесты
Присутствуют и тестируют каждый из CRUD-элементов.
