# First MCP Server

This repository demonstrates how to easily convert a standard Spring Boot application into a Model Context Protocol (MCP) server. 

By leveraging Spring AI, you can expose your application's logic as tools to AI assistants (like Claude) using just three core concepts: `@Tool`, `@ToolParam`, and a simple configuration class.

## 1. `@Tool`

The `@Tool` annotation is used on any method in your Spring components to mark it as an AI-callable tool. You provide a `name` and a `description` that the AI will use to understand what the tool does and when to call it.

```java
@Tool(name = "getAllUsers", description = "Retrieves all users from the system.")
public List<Users> getAllUsers() {
    return usersRepository.findAll();
}
```

## 2. `@ToolParam`

When your tool requires input arguments, you use the `@ToolParam` annotation to describe each parameter. This helps the AI understand exactly what information it needs to pass to your method. (Note: standard parameter names can also be inferred if compiled with `-parameters`, but `@ToolParam` allows you to be explicit and provide detailed descriptions).

```java
@Tool(name = "addUser", description = "Adds a new user to the system.")
public Users addUser(
    @ToolParam(description = "The full name of the user") String name, 
    @ToolParam(description = "The user's email address") String email
) {
    return usersRepository.save(new Users(name, email, null));
}
```

## 3. `McpConfig.java`

While `@Tool` marks the methods, Spring AI's MCP auto-configuration requires you to explicitly register these tools. You do this by defining a `ToolCallbackProvider` bean in a `@Configuration` class. This effectively bridges your tools to the MCP server instance, making them visible to any connected MCP client.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import com.dharmaraj.firstmcpserver.services.UsersService;

@Configuration
public class McpConfig {
    
    @Bean
    public ToolCallbackProvider usersTools(UsersService usersService) {
        // Scans the UsersService for @Tool annotations and exports them to the MCP Server
        return MethodToolCallbackProvider.builder().toolObjects(usersService).build();
    }
}
```

With just these three pieces—annotating methods, describing parameters, and registering the provider—your Spring Boot application is ready to function as an MCP server!
