package com.dharmaraj.firstmcpserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import com.dharmaraj.firstmcpserver.services.UsersService;

@Configuration
public class McpConfig {
    @Bean
    public ToolCallbackProvider usersTools(UsersService usersService) {
        return MethodToolCallbackProvider.builder().toolObjects(usersService).build();
    }
}
