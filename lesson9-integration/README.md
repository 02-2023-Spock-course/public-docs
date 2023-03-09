Приложение, демонстрирующее асинхронную и многопоточную работу.
На Spock показан пример сложного интеграционного теста, использующего возможности Spring WebMvc и утилиту MockServer. 

Все примеры рабочие. Проект можно собрать и запускать готовые тесты

Пример вызова:
curl -v -i -X POST http://localhost:8085/study-group -H 'content-type: application/json' -d '{"name":"group1", "studentCount": 5}'
