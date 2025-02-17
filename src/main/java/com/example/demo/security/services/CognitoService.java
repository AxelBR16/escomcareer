package com.example.demo.security.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserType;

@Service
public class CognitoService {
    private final CognitoIdentityProviderClient cognitoClient;

    @Value(value = "us-east-1_FgnMQXp8L")
    private String userPoolId;


    public CognitoService() {
        this.cognitoClient = CognitoIdentityProviderClient.builder()
                .build();
    }
    public String getUserEmail(String username) {
        AdminGetUserRequest request = AdminGetUserRequest.builder()
                .userPoolId(userPoolId)
                .username(username)
                .build();

        AdminGetUserResponse response = cognitoClient.adminGetUser(request);

        return response.userAttributes().stream()
                .filter(attr -> attr.name().equals("email"))
                .map(attr -> attr.value())
                .findFirst()
                .orElse(null);
    }
}

