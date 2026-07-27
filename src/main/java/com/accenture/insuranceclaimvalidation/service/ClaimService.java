package com.accenture.insuranceclaimvalidation.service;

import org.springframework.web.multipart.MultipartFile;

import com.accenture.insuranceclaimvalidation.dto.request.ClaimRequest;
import com.accenture.insuranceclaimvalidation.dto.response.ClaimResponse;
import com.accenture.insuranceclaimvalidation.dto.response.FileUploadResponse;

public interface ClaimService {

    ClaimResponse processClaim(ClaimRequest request);

    FileUploadResponse uploadClaim(MultipartFile file);
}