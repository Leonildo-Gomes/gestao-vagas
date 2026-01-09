# Arquitetura do Projeto: Gestão de Cursos

Este documento descreve a arquitetura e o plano de implementação para a API de Gestão de Cursos, seguindo princípios de **Clean Architecture** e **Monólito Modular**.

## 🏗️ Estrutura de Pastas

A organização é feita por módulos de domínio, permitindo escalabilidade e isolamento de responsabilidades.

```text
src/main/java/com/leonildo/gestao_cursos/
├── shared/                  # Código compartilhado entre módulos (Exceptions, Utils, DTOs globais)
└── modules/
    └── course/              # Módulo de Cursos (Independente)
        ├── domain/          # O "Coração" do negócio (Independente de frameworks)
        │   ├── entity/      # Entidades de domínio (Regras de negócio cruciais)
        │   ├── exception/   # Exceções específicas do domínio
        │   └── port/        # Interfaces (Contratos) para Repositórios e Serviços
        ├── usecases/        # Regras de Aplicação (Service Layer)
        │   └── create_course/ # Funcionalidades agrupadas por contexto (SRP)
        └── infra/           # Detalhes técnicos e ferramentas (Spring, BD, etc)
            ├── controller/  # Adaptadores de entrada (REST Controllers)
            ├── persistence/ # Implementação de banco de dados (JPA Repositories)
            └── config/      # Beans e configurações específicas do módulo
```

## 🛡️ Camadas e Responsabilidades

1.  **Domain**: Contém a lógica de negócio pura. Se trocarmos o banco de dados ou o framework web, esta camada permanece intocada.
2.  **Use Cases**: Define o "o que" o sistema faz. Orquestra o fluxo de dados entre o domínio e a infraestrutura.
3.  **Infrastructure**: Define o "como" o sistema faz tecnicamente. Aqui lidamos com anotações Spring, JSON, SQL e integrações externas.

## 🚀 Plano de Implementação (Fase 1: CRUD de Cursos)

1.  **Setup do Ambiente**:
    *   Correção do `pom.xml` para Spring Boot 3.4.1.
    *   Inclusão de dependências: JPA, Validation, H2, Lombok.
2.  **Modelagem de Domínio**:
    *   Criação da entidade `Course` com atributos: `id`, `name`, `category`, `status`, `createdAt`, `updatedAt`.
    *   Definição da interface `CourseRepository` (Port).
3.  **Desenvolvimento do Fluxo de Criação**:
    *   Implementação do `CreateCourseUseCase`.
    *   Criação do `CourseController` com endpoint `POST /courses`.
4.  **Persistência**:
    *   Configuração do H2 e criação da implementação JPA do repositório.
5.  **Qualidade**:
    *   Testes unitários para o Caso de Uso.
    *   Testes de integração para os Endpoints.

---
*Documento gerado por Gemini CLI Agent - Engenheiro de Software Sênior.*
