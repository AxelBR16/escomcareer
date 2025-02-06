package com.example.demo.security.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.example.demo.security.dtos.SignUpDto;
import com.example.demo.security.dtos.SignInDto;
import com.example.demo.security.entities.Usuario;
import com.example.demo.security.enums.RoleName;
import com.example.demo.security.repositories.RoleRepository;
import com.example.demo.security.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Value(value = "${aws.cognito.ClientId}")
    private String cognitoClientId;

    public UsuarioService(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
    }

    @Autowired
    private RoleService roleService;

    public void crearUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void signUpUsuario(SignUpDto signUpDto) {
        AttributeType attributeType = new AttributeType().withName("email").withValue(signUpDto.getEmail());
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.withClientId(cognitoClientId)
                .withPassword(signUpDto.getPassword())
                .withUsername(signUpDto.getEmail())
                .withUserAttributes(attributeType);
        awsCognitoIdentityProvider.signUp(signUpRequest);

        Usuario usuario = new Usuario();
        usuario.setEmail(signUpDto.getEmail());
        usuario.setNombre(signUpDto.getNombre());
        usuario.setApellido(signUpDto.getApellido());
        usuario.setRole(roleService.getRoleByName(RoleName.ROLE_ADMIN).get());
        crearUsuario(usuario);
    }

    public String singInUser(SignInDto signInDto) {
        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", signInDto.getEmail());
        authParams.put("PASSWORD", signInDto.getPassword());

        InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(cognitoClientId)
                .withAuthParameters(authParams);

        InitiateAuthResult initiateAuthResult = awsCognitoIdentityProvider.initiateAuth(initiateAuthRequest);

        return initiateAuthResult.getAuthenticationResult().getAccessToken();
    }

    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
