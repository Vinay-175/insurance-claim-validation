package com.accenture.insuranceclaimvalidation.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.insuranceclaimvalidation.dto.ClaimDetails;
import com.accenture.insuranceclaimvalidation.dto.request.ClaimRequest;
import com.accenture.insuranceclaimvalidation.dto.response.ClaimResponse;
import com.accenture.insuranceclaimvalidation.dto.response.FileUploadResponse;
import com.accenture.insuranceclaimvalidation.dto.response.ValidationResult;
import com.accenture.insuranceclaimvalidation.exception.InvalidFileException;
import com.accenture.insuranceclaimvalidation.service.AIService;
import com.accenture.insuranceclaimvalidation.service.ClaimService;
import com.accenture.insuranceclaimvalidation.service.DocumentProcessingService;
import com.accenture.insuranceclaimvalidation.service.validation.ClaimValidationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final DocumentProcessingService documentProcessingService;
    private final AIService aiService;
    private final ClaimValidationService claimValidationService;

    @Override
    public ClaimResponse processClaim(ClaimRequest request) {

        log.info("Processing claim request: {}", request);

        return ClaimResponse.builder()
                .status("SUCCESS")
                .message("Claim received successfully.")
                .claimId("TEMP001")
                .recommendation("Pending Validation")
                .build();
    }

    @Override
    public FileUploadResponse uploadClaim(MultipartFile file) {

        if (file.isEmpty()) {
            log.error("Uploaded file is empty.");
            throw new InvalidFileException("Uploaded file is empty.");
        }

        if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {
            log.error("Invalid file type. Only PDF files are allowed.");
            throw new InvalidFileException("Only PDF files are allowed.");
        }

        String extractedText = documentProcessingService.processDocument(file);

        log.info("Document text extracted successfully.");

        ClaimDetails claimDetails = aiService.extractClaimDetails(extractedText);

        ValidationResult validationResult = claimValidationService.validate(claimDetails);

        log.info("Claim details extracted successfully using AI.");

        return FileUploadResponse.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .message("File uploaded successfully.")
                .extractedText(extractedText)
                .claimDetails(claimDetails)
                .validationResult(validationResult)
                .build();
    }
}