package com.accenture.insuranceclaimvalidation.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.accenture.insuranceclaimvalidation.dto.ClaimDetails;
import com.accenture.insuranceclaimvalidation.exception.AIException;
import com.accenture.insuranceclaimvalidation.service.AIService;
import com.accenture.insuranceclaimvalidation.util.PromptTemplates;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroqAIServiceImpl implements AIService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    @Override
    public ClaimDetails extractClaimDetails(String extractedText) {

        try {

            String prompt = PromptTemplates.buildClaimExtractionPrompt(extractedText);

            String aiResponse = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            System.out.println("\n========== AI RESPONSE ==========");
            
            System.out.println("=================================\n");

            return objectMapper.readValue(aiResponse, ClaimDetails.class);

        } catch (Exception e) {

            throw new AIException("Failed to process AI response.", e);

        }

    }

}