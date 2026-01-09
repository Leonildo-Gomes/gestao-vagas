# Guia de Boas Práticas e Refatoração (Java 21 + Spring Boot 3)

Este documento descreve padrões recomendados para elevar a qualidade do código do projeto `gestao_cursos`, focando em manutenibilidade, legibilidade e robustez.

## 1. Tratamento Global de Erros (`GlobalExceptionHandler`)

**Problema:** Controllers poluídos com blocos `try-catch` repetitivos.
**Solução:** Centralizar o tratamento de erros usando `@RestControllerAdvice`.

Isso permite que os controllers foquem apenas no "Caminho Feliz" (Happy Path), enquanto os erros são capturados e formatados automaticamente em um único lugar.

### Exemplo de Implementação

Crie o arquivo em: `src/main/java/com/leonildo/gestao_cursos/shared/infra/exception/GlobalExceptionHandler.java`

```java
package com.leonildo.gestao_cursos.shared.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Captura erros de validação (@Valid nos DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // 2. Captura exceções genéricas de runtime (ex: Regra de Negócio violada)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        // Idealmente, crie uma classe de erro padrão (ErrorResponseDTO)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    // 3. Captura erros inesperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        ex.printStackTrace(); // Logar o erro real no servidor
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Ocorreu um erro interno inesperado."));
    }
}
```

### Como fica o Controller (Antes vs. Depois)

**Antes (Try-Catch Hell):**
```java
@PostMapping("/")
public ResponseEntity<Object> create(@RequestBody CreateCourseInputDTO input) {
    try {
        var result = this.createCourseUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Exception e) { // Repetido em TODOS os métodos
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
```

**Depois (Clean):**
```java
@PostMapping("/")
public ResponseEntity<CreateCourseOutputDTO> create(@Valid @RequestBody CreateCourseInputDTO input) {
    // Se der erro, o GlobalExceptionHandler captura. O código flui.
    var result = this.createCourseUseCase.execute(input);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
}
```

---

## 2. Injeção de Dependência via Construtor

**Problema:** Uso de `@Autowired` em campos (Field Injection) dificulta testes unitários (pois obriga a subir o contexto do Spring ou usar Reflection) e esconde dependências obrigatórias.

**Solução:** Usar `@RequiredArgsConstructor` do Lombok.

```java
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor // Gera construtor para campos 'final'
public class CourseController {

    // O compilador garante que isso não será null após a construção
    private final CreateCourseUseCase createCourseUseCase; 
    
    // ... métodos
}
```

## 3. Uso de `Records` para DTOs (Java 16+)

Já estamos utilizando, mas vale reforçar:

```java
// Imutável, conciso, equals/hashCode automáticos
public record CreateCourseInputDTO(
    @NotBlank(message = "O nome é obrigatório") 
    String name,
    
    @NotBlank 
    String category
) {}
```

## 4. Separação de DTOs e Entities

**Regra:** Nunca retorne a `Entity` (JPA) diretamente no Controller.
*   Evita "LazyInitializationException".
*   Evita expor dados sensíveis (ex: senha, logs internos).
*   Permite mudar o banco de dados sem quebrar a API.

Use métodos estáticos de conversão ou bibliotecas como MapStruct, mas mantenha a conversão fora da lógica principal do Controller.
