package com.example.demo.security.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import org.springframework.stereotype.Service;

@Service
public class CognitoService {
    private final AWSCognitoIdentityProvider cognitoClient;
    private final String userPoolId = "us-east-1_FgnMQXp8L";

    public CognitoService(AWSCognitoIdentityProvider cognitoClient) {
        this.cognitoClient = cognitoClient;
    }

    public String getUserEmail(String username) {
        AdminGetUserRequest request = new AdminGetUserRequest()
                .withUserPoolId(userPoolId)
                .withUsername(username);

        AdminGetUserResult response = cognitoClient.adminGetUser(request);

        return response.getUserAttributes().stream()
                .filter(attr -> attr.getName().equals("email"))
                .map(attr -> attr.getValue())
                .findFirst()
                .orElse(null);
    }
}
