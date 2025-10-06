package com.yupi.codertestbackend.config;

import com.yupi.codertestbackend.service.ai.InterviewQuestionSearchTool;
import com.yupi.codertestbackend.service.ai.LevelGenerationAiService;
import com.yupi.codertestbackend.service.ai.ResultReportAiService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI 配置类
 */
@Data
@ConfigurationProperties(prefix = "langchain4j")
@Configuration
public class AiConfig {
    private String baseUrl;
    private String apiKey;
    private String modelName;
    private Double temperature;
    
    @Resource
    private InterviewQuestionSearchTool interviewQuestionSearchTool;

    /**
     * 配置 ChatModel Bean
     */
    @Bean
    public ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(temperature)
                .build();
    }

    /**
     * 配置关卡生成 AI 服务
     */
    @Bean
    public LevelGenerationAiService levelGenerationAiService(ChatModel chatModel) {
        return AiServices.builder(LevelGenerationAiService.class)
                .chatModel(chatModel)
                .build();
    }

    /**
     * 配置结果报告生成 AI 服务
     */
    @Bean
    public ResultReportAiService resultReportAiService(ChatModel chatModel) {
        return AiServices.builder(ResultReportAiService.class)
                .chatModel(chatModel)
                .tools(interviewQuestionSearchTool)
                .build();
    }
}
