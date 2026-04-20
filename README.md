# 🗑️ WasteWatch API

API RESTful desenvolvida em **Spring Boot** para **gestão inteligente de resíduos e reciclagem**, com autenticação, alertas automáticos e integração com banco **Oracle** via **Docker**.

## 📦 Tema ESG
**Gestão de Resíduos e Reciclagem**

- Rastreamento da coleta seletiva e descarte correto de resíduos.  
- Alertas automáticos quando um ponto de coleta atinge o limite configurado.  
- Integração com banco Oracle para armazenamento persistente.  
- Sistema seguro com autenticação básica via Spring Security.

---

## 🚀 Funcionalidades Principais

| Função | Endpoint | Método | Autenticação | Descrição |
|--------|-----------|--------|---------------|------------|
| ♻️ Listar Pontos de Coleta | `/api/collection-points` | GET | USER / ADMIN | Lista todos os pontos de coleta cadastrados |
| ➕ Cadastrar Ponto | `/api/collection-points` | POST | ADMIN | Cria um novo ponto de coleta |
| ✏️ Atualizar Ponto | `/api/collection-points/{id}` | PUT | ADMIN | Atualiza informações de um ponto |
| ❌ Deletar Ponto | `/api/collection-points/{id}` | DELETE | ADMIN | Remove um ponto de coleta |
| 🚛 Listar Coletas | `/api/pickups` | GET | USER / ADMIN | Mostra as coletas registradas |
| 📅 Agendar Coleta | `/api/pickups/schedule` | POST | ADMIN | Cria um novo agendamento de coleta |
| ✅ Marcar como Coletado | `/api/pickups/{id}/complete` | PUT | ADMIN | Marca a coleta como concluída |
| 🚨 Listar Alertas | `/api/alerts` | GET | USER / ADMIN | Lista pontos que atingiram ou ultrapassaram o limite configurado |

---

## 🔐 Autenticação

A API usa **Spring Security** com autenticação **Basic Auth** (usuário/senha).  

### Usuários padrão:

| Usuário | Senha | Permissão |
|----------|--------|------------|
| `admin` | `[REDACTED]` | ADMIN, USER |
| `user` | `[REDACTED]` | USER |

Use esses usuários no **Postman** em:  
**Authorization → Basic Auth**

---

## 🧱 Estrutura do Projeto

```
wastewatch/
 ├── src/
 │   ├── main/java/com/teko/wastewatch/
 │   │   ├── controller/    → Controladores REST
 │   │   ├── dto/           → Objetos de transferência (ex: AlertDto)
 │   │   ├── model/         → Entidades JPA
 │   │   ├── repository/    → Repositórios JPA
 │   │   ├── service/       → Lógica de negócio
 │   │   └── WastewatchApplication.java
 │   └── resources/
 │       ├── application.yml
 │       └── db/migration/  → Scripts Flyway (Oracle)
 ├── Dockerfile
 ├── docker-compose.yml
 └── pom.xml
```

---

## 🐳 Docker

### **Dockerfile**

```dockerfile
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/wastewatch-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
```

### **docker-compose.yml**

```yaml
version: "3.9"
services:
  oracle:
    image: gvenzl/oracle-xe:21-slim
    environment:
      - ORACLE_PASSWORD=[REDACTED]
      - APP_USER=WASTE
      - APP_USER_PASSWORD=[REDACTED]
    ports:
      - "1521:1521"
    healthcheck:
      test: ["CMD-SHELL", "echo 'select 1 from dual;' | sqlplus -s system/[REDACTED]@localhost:1521/FREEPDB1 | grep 1"]
      interval: 10s
      timeout: 5s
      retries: 10

  app:
    build: .
    depends_on:
      oracle:
        condition: service_healthy
    environment:
      - SPRING_DATASOURCE_URL=jdbc:oracle:thin:@//oracle:1521/FREEPDB1
      - SPRING_DATASOURCE_USERNAME=WASTE
      - SPRING_DATASOURCE_PASSWORD=[REDACTED]
    ports:
      - "8080:8080"
```

### **Comandos**

```bash
# Build do projeto
mvn clean package -DskipTests

# Subir containers
docker compose up --build

# Acessar API
http://localhost:8080/api/collection-points
```

---

## 🧠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Security**
- **Spring Data JPA**
- **Flyway Migration**
- **Oracle XE 21**
- **Docker / Docker Compose**

---

## 📄 Como Testar no Postman

1. Importe o arquivo `WasteWatch.postman_collection.json`
2. Use o ambiente `localhost:8080`
3. Faça login com:
   - user/[REDACTED] → para testar listagens
   - admin/[REDACTED] → para cadastrar/editar/excluir
4. Teste os seguintes endpoints:
   - `GET /api/collection-points`
   - `POST /api/collection-points`
   - `GET /api/alerts` → mostra pontos acima do limite

---

## 📬 Retornos Esperados

### `GET /api/alerts`
```json
[
  {
    "collectionPointId": 1,
    "name": "Ponto Central",
    "capacityKg": 100,
    "currentLevelKg": 90,
    "thresholdPercent": 80,
    "fillPercent": 90
  }
]
```

---

## 🏁 Status do Projeto
✅ Finalizado e funcional  
☑️ Atende a todos os requisitos obrigatórios (Oracle, Docker, Spring Security, Validação, RESTful, Alerts)

---
