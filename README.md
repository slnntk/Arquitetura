# Arquiteturas Spring Boot

Este repositório demonstra duas arquiteturas diferentes para aplicações Spring Boot:

1. **Arquitetura em Camadas (Layered Architecture)**
2. **Arquitetura Hexagonal (Hexagonal Architecture)**

## Estrutura do Projeto

O projeto está organizado como um multi-módulo Gradle com dois módulos independentes:

```
arquiteturas/
├── layered-architecture/          # Arquitetura em camadas tradicional
└── hexagonal-architecture/        # Arquitetura hexagonal (ports & adapters)
```

## 1. Arquitetura em Camadas (Layered Architecture)

### Localização
`layered-architecture/`

### Estrutura
```
src/main/java/com/unifor/cc/arquiteturas/layers/
├── controllers/     # Camada de Apresentação (REST Controllers)
├── services/        # Camada de Negócio (Business Logic)
├── repository/      # Camada de Acesso a Dados (Data Access)
└── models/          # Entidades/Modelos
```

### Características
- **Separação por camadas horizontais**
- Controller → Service → Repository
- Cada camada só se comunica com a camada imediatamente inferior
- Fácil de entender e implementar
- Amplamente utilizada em aplicações tradicionais

### Porta
- **Layered Architecture**: `http://localhost:8080`

## 2. Arquitetura Hexagonal (Hexagonal Architecture)

### Localização
`hexagonal-architecture/`

### Estrutura
```
src/main/java/com/unifor/cc/arquiteturas/hexagonal/
├── domain/
│   ├── model/       # Entidades de Domínio
│   ├── port/
│   │   ├── in/      # Ports de Entrada (Use Cases)
│   │   └── out/     # Ports de Saída (Repository Interfaces)
│   └── service/     # Serviços de Domínio
└── adapter/
    ├── in/
    │   └── web/     # Adaptadores de Entrada (Controllers)
    └── out/
        └── persistence/ # Adaptadores de Saída (JPA Implementation)
```

### Características
- **Separação por responsabilidades e direção de dependências**
- Domínio no centro, isolado de detalhes externos
- Inversão de dependências através de Ports e Adapters
- Fácil de testar (domain independente)
- Flexível para mudanças de tecnologia

### Porta
- **Hexagonal Architecture**: `http://localhost:8081`

## Como Executar

### Pré-requisitos
- Java 21
- Gradle (incluído via wrapper)

### Executar Ambos os Projetos

1. **Build do projeto completo:**
```bash
./gradlew clean build
```

2. **Executar Arquitetura em Camadas:**
```bash
./gradlew :layered-architecture:bootRun
```

3. **Executar Arquitetura Hexagonal (em outro terminal):**
```bash
./gradlew :hexagonal-architecture:bootRun
```

### Executar Testes

```bash
# Todos os testes
./gradlew test

# Apenas layered architecture
./gradlew :layered-architecture:test

# Apenas hexagonal architecture
./gradlew :hexagonal-architecture:test
```

## APIs Disponíveis

Ambas as arquiteturas implementam as mesmas operações CRUD:

### Endpoints
```
GET    /api/users          # Listar todos os usuários
GET    /api/users/{id}     # Buscar usuário por ID
POST   /api/users          # Criar usuário
PUT    /api/users/{id}     # Atualizar usuário
DELETE /api/users/{id}     # Deletar usuário
```

### Exemplo de Payload
```json
{
  "name": "João Silva",
  "email": "joao@exemplo.com"
}
```

## Banco de Dados

Ambos os projetos utilizam:
- **H2 Database** (in-memory)
- **Console H2** habilitado para desenvolvimento
  - Layered: `http://localhost:8080/h2-console`
  - Hexagonal: `http://localhost:8081/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb` (layered) ou `jdbc:h2:mem:hexagonal-db` (hexagonal)
  - User: `sa` / Password: `password`

## Comparação das Arquiteturas

| Aspecto | Layered Architecture | Hexagonal Architecture |
|---------|---------------------|------------------------|
| **Complexidade** | Baixa | Média |
| **Flexibilidade** | Média | Alta |
| **Testabilidade** | Boa | Excelente |
| **Acoplamento** | Médio | Baixo |
| **Curva de Aprendizado** | Suave | Íngreme |
| **Manutenibilidade** | Boa | Excelente |
| **Adequação** | Projetos simples/médios | Projetos complexos |

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Web**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Gradle 8.14.3**

## Conclusão

- **Use Layered Architecture quando**: O projeto for relativamente simples, a equipe estiver familiarizada com o padrão, e não houver necessidade de alta flexibilidade.

- **Use Hexagonal Architecture quando**: O projeto for complexo, houver necessidade de alta testabilidade, múltiplas interfaces de entrada/saída, ou quando a regra de negócio for crítica e precisar estar isolada.