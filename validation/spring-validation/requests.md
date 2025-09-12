curl -X POST http://localhost:8080/card \
-H "Content-Type: application/json" \
-d '{"card": "22023217356007283"}'

curl -X POST http://localhost:8080/users \
-H "Content-Type: application/json" \
-d '{"name": "John Doe", "email":"jd@mail.com", "age":"18"}'

