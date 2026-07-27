package com.accenture.insuranceclaimvalidation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.insuranceclaimvalidation.dto.request.ClaimRequest;
import com.accenture.insuranceclaimvalidation.dto.response.ClaimResponse;
import com.accenture.insuranceclaimvalidation.dto.response.FileUploadResponse;
import com.accenture.insuranceclaimvalidation.service.ClaimService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/claims")
@Validated
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping
    public ResponseEntity<ClaimResponse> processClaim(
            @Valid @RequestBody ClaimRequest request) {

        ClaimResponse response = claimService.processClaim(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadClaim(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(claimService.uploadClaim(file));
    }
}