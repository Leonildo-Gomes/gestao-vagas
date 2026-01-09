# Gestão de Cursos API

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green)](https://spring.io/projects/spring-boot)

API REST para gerenciamento de cursos de programação, desenvolvida com foco em Clean Architecture, modularidade e boas práticas modernas de Engenharia de Software.

## 🚀 Tecnologias

* **Java 21**: Versão LTS mais recente.
* **Spring Boot 4.0.1**: Framework base.
* **Spring Data JPA**: Persistência de dados.
* **PostgreSQL**: Banco de dados relacional.
* **Lombok**: Redução de boilerplate code.
* **Bean Validation**: Validação de dados de entrada.

## 🏗️ Arquitetura

O projeto segue uma arquitetura de **Monólito Modular** baseada em **Clean Architecture**.

```text
src/main/java/com/leonildo/gestao_cursos/
├── shared/                  # Utilitários globais e tratamento de erros
└── modules/
    └── course/              # Módulo autocontido de Cursos
        ├── domain/          # Entidades e Interfaces (Core Business)
        ├── usecases/        # Regras de Aplicação (Service Layer)
        └── infra/           # Frameworks, BD e Controllers
```

## 🛠️ Como Executar

1. **Pré-requisitos**: Certifique-se de ter o Java 21 instalado.
2. **Clonar o repositório**:

    ```bash
    git clone https://github.com/seu-usuario/gestao_cursos.git
    cd gestao_cursos
    ```

3. **Rodar a aplicação**:

    ```bash
    ./mvnw spring-boot:run
    ```

    A aplicação iniciará na porta `8080`.

## 🔌 Endpoints Principais

### Cursos (`/course`)

* `POST /course/`: Cria um novo curso.
  * Body: `{ "name": "Java Spring", "category": "Backend" }`
* `GET /course/`: Lista todos os cursos.
* `GET /course/?name=Java&category=Backend`: Busca cursos por nome e categoria.

---
Desenvolvido como parte dos estudos de Spring Boot na Rocketseat.
