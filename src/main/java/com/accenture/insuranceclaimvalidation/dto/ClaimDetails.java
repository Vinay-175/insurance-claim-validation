package com.accenture.insuranceclaimvalidation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDetails {

    private String patientName;

    private String policyNumber;

    private String hospitalName;

    private String diagnosis;

    private String admissionDate;

    private String dischargeDate;

    private String claimAmount;

    private String doctorName;

}