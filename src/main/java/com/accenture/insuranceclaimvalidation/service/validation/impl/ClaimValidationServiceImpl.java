package com.accenture.insuranceclaimvalidation.service.validation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.accenture.insuranceclaimvalidation.dto.ClaimDetails;
import com.accenture.insuranceclaimvalidation.dto.response.ValidationResult;
import com.accenture.insuranceclaimvalidation.service.validation.ClaimValidationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class ClaimValidationServiceImpl implements ClaimValidationService {

    @Override
    public ValidationResult validate(ClaimDetails claimDetails) {

        List<String> errors = new ArrayList<>();

        validatePatientName(claimDetails, errors);
        validatePolicyNumber(claimDetails, errors);
        validateHospitalName(claimDetails, errors);
        validateDiagnosis(claimDetails, errors);
        validateAdmissionDate(claimDetails, errors);
        validateDischargeDate(claimDetails, errors);
        validateClaimAmount(claimDetails, errors);
        validateDoctorName(claimDetails, errors);
        validateDateSequence(claimDetails, errors);
        validateClaimAmountValue(claimDetails, errors);

        return ValidationResult.builder()
                .valid(errors.isEmpty())
                .errors(errors)
                .build();
    }

    private void validatePatientName(ClaimDetails claimDetails,
            List<String> errors) {

        if (claimDetails.getPatientName() == null
                || claimDetails.getPatientName().isBlank()) {

            errors.add("Patient Name is mandatory.");
        }

    }

    private void validatePolicyNumber(ClaimDetails claimDetails,
            List<String> errors) {

        if (claimDetails.getPolicyNumber() == null
                || claimDetails.getPolicyNumber().isBlank()) {

            errors.add("Policy Number is mandatory.");
        }

    }

    private void validateHospitalName(ClaimDetails claimDetails,
            List<String> errors) {

        if (claimDetails.getHospitalName() == null
                || claimDetails.getHospitalName().isBlank()) {

            errors.add("Hospital Name is mandatory.");
        }

    }

    private void validateDiagnosis(ClaimDetails claimDetails,
            List<String> errors) {

        if (claimDetails.getDiagnosis() == null
                || claimDetails.getDiagnosis().isBlank()) {

            errors.add("Diagnosis is mandatory.");
        }

    }

    private void validateAdmissionDate(ClaimDetails claimDetails,
            List<String> errors) {

        if (claimDetails.getAdmissionDate() == null
                || claimDetails.getAdmissionDate().isBlank()) {

            errors.add("Admission Date is mandatory.");
        }

    }

    private void validateDischargeDate(ClaimDetails claimDetails,
            List<String> errors) {

        if (claimDetails.getDischargeDate() == null
                || claimDetails.getDischargeDate().isBlank()) {

            errors.add("Discharge Date is mandatory.");
        }

    }

    private void validateClaimAmount(ClaimDetails claimDetails,
            List<String> errors) {

        if (claimDetails.getClaimAmount() == null
                || claimDetails.getClaimAmount().isBlank()) {

            errors.add("Claim Amount is mandatory.");
        }

    }

    private void validateDoctorName(ClaimDetails claimDetails,
            List<String> errors) {

        if (claimDetails.getDoctorName() == null
                || claimDetails.getDoctorName().isBlank()) {

            errors.add("Doctor Name is mandatory.");
        }

    }

    private void validateDateSequence(ClaimDetails claimDetails,
            List<String> errors) {

        if (claimDetails.getAdmissionDate() == null
                || claimDetails.getAdmissionDate().isBlank()
                || claimDetails.getDischargeDate() == null
                || claimDetails.getDischargeDate().isBlank()) {

            return;
        }

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate admissionDate = LocalDate.parse(claimDetails.getAdmissionDate(), formatter);

            LocalDate dischargeDate = LocalDate.parse(claimDetails.getDischargeDate(), formatter);

            if (dischargeDate.isBefore(admissionDate)) {
                errors.add("Discharge Date cannot be before Admission Date.");
            }

        } catch (DateTimeParseException ex) {
            errors.add("Invalid date format. Expected dd-MM-yyyy.");
        }
    }

    private void validateClaimAmountValue(ClaimDetails claimDetails,
            List<String> errors) {

        if (claimDetails.getClaimAmount() == null
                || claimDetails.getClaimAmount().isBlank()) {
            return;
        }

        try {

            String amountText = claimDetails.getClaimAmount()
                    .replace(",", "")
                    .replace("₹", "")
                    .trim();

            BigDecimal amount = new BigDecimal(amountText);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                errors.add("Claim Amount must be greater than zero.");
            }

            BigDecimal maximumAllowed = new BigDecimal("1000000");

            if (amount.compareTo(maximumAllowed) > 0) {
                errors.add("Claim Amount exceeds the allowed limit.");
            }

        } catch (NumberFormatException ex) {
            errors.add("Claim Amount must be numeric.");
        }
    }

}