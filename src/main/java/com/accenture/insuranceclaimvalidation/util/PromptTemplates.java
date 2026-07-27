package com.accenture.insuranceclaimvalidation.util;

public final class PromptTemplates {

    private PromptTemplates() {
        // Prevent instantiation
    }

    public static String buildClaimExtractionPrompt(String extractedText) {

        return """
                You are an expert Insurance Claim Processing Assistant.

                Your task is to extract the claim details from the provided insurance claim document.

                Rules:
                1. Return ONLY valid JSON.
                2. Do NOT return markdown.
                3. Do NOT return explanation.
                4. Do NOT return comments.
                5. Do NOT return any additional text.
                6. If a field is missing, return an empty string ("").
                7. claimAmount should contain only the numeric value.
                8. Dates should be returned in the same format as found in the document.

                Return the response in exactly the following JSON format:

                {
                  "patientName": "",
                  "policyNumber": "",
                  "hospitalName": "",
                  "diagnosis": "",
                  "admissionDate": "",
                  "dischargeDate": "",
                  "claimAmount": "",
                  "doctorName": ""
                }

                Claim Document:

                %s
                """.formatted(extractedText);

    }
}