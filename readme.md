
#_Multithread Port Scanner_
##_Задача_

Разработать многопоточное приложение сканер TCP-портов.

Полное описание [здесь](task.jpg).


##Проект состоит из классов
- **DataParser** - служит для обработки входных данных 
и заполнения списка портов и хостов для дальнейшего сканирования.

- **JSONWriter** - записывает результат сканирования в файл _JSON_ формата.

- **Main** - вызывает методы для парсинга входных данных,перемешивания 
списка хостов и портов,отправляет задачи на выполнение в _ExecutorService_.После завершения всех задач,
вызывает метод для записи результатов.

- **MyThread** - поток,который с помощью класса Socket сканирует определенный хост и порт.
