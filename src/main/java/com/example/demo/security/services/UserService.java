package com.example.demo.security.services;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.example.demo.domain.entities.Carrera;
import com.example.demo.domain.repositories.CarreraRepository;
import com.example.demo.security.dtos.*;
import com.example.demo.security.entities.Administrador;
import com.example.demo.security.entities.Aspirante;
import com.example.demo.security.entities.Egresado;
import com.example.demo.security.repositories.AspiranteRepository;
import com.example.demo.security.repositories.EgresadoRepository;
import com.example.demo.security.repositories.AdministradorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private AspiranteRepository aspiranteRepository;

    @Autowired
    private EgresadoRepository egresadoRepository;

    @Autowired
    private CarreraRepository carreraRepository;



    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Value(value = "${aws.cognito.ClientId}")
    private String cognitoClientId;

    @Value(value = "${aws.cognito.userPoolId}")
    private String userPoolId;



    public UserService(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
    }

    public void crearAdmin(Administrador administrador) {
        administradorRepository.save(administrador);
    }
    public void crearEgresado(Egresado egresado) {
        egresadoRepository.save(egresado);
    }

    public Carrera obtenerCarreraPorId(Integer idCarrera) {
        return carreraRepository.findById(idCarrera).orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
    }

    public void crearAspirante(Aspirante aspirante) {
        aspiranteRepository.save(aspirante);
    }


    public void signUpUsuario(@Valid UsuarioDto signUpDto) {
        AttributeType attributeType = new AttributeType().withName("email").withValue(signUpDto.getEmail());
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.withClientId(cognitoClientId)
                .withPassword(signUpDto.getPassword())
                .withUsername(signUpDto.getEmail())
                .withUserAttributes(attributeType);
        awsCognitoIdentityProvider.signUp(signUpRequest);

        String roleName = signUpDto.getRole();

        if (roleName.equals("ROLE_ADMIN")) {
            Administrador administrador = new Administrador();
            administrador.setEmail(signUpDto.getEmail());
            administrador.setNombre(signUpDto.getNombre());
            administrador.setApellido(signUpDto.getApellido());
            crearAdmin(administrador);
        } else if (roleName.equals("ROLE_ASPIRANTE")) {
            Aspirante aspirante = new Aspirante();
            aspirante.setEmail(signUpDto.getEmail());
            aspirante.setNombre(signUpDto.getNombre());
            aspirante.setApellido(signUpDto.getApellido());
            crearAspirante(aspirante);
        } else if (roleName.equals("ROLE_EGRESADO")) {
            Egresado egresado = new Egresado();
            egresado.setEmail(signUpDto.getEmail());
            egresado.setNombre(signUpDto.getNombre());
            egresado.setApellido(signUpDto.getApellido());
            Carrera carrera = obtenerCarreraPorId(signUpDto.getCarrera());
            egresado.setCarrera(carrera);
            crearEgresado(egresado);
        } else {
            throw new IllegalArgumentException("Rol de usuario no válido");
        }
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

    public Optional<Administrador> getAdministradorByEmail(String email) {
        return administradorRepository.findByEmail(email);
    }

    public Optional<Aspirante> getAspiranteByEmail(String email) {
        return aspiranteRepository.findByEmail(email);
    }
    public Optional<Egresado> getEgresadoByEmail(String email) {return egresadoRepository.findByEmail(email);}


    public void signOutUser(String accessToken) {
        String accessTokenl = accessToken.replace("Bearer ", "").trim();
        GlobalSignOutRequest signOutRequest = new GlobalSignOutRequest()
                .withAccessToken(accessTokenl);
        awsCognitoIdentityProvider.globalSignOut(signOutRequest);
    }

    public void forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest()
                .withClientId(cognitoClientId)
                .withUsername(forgotPasswordDto.getEmail());
        awsCognitoIdentityProvider.forgotPassword(forgotPasswordRequest);
    }

    public boolean isEmailRegistered(String email) {
        try {
            AdminGetUserRequest adminGetUserRequest = new AdminGetUserRequest()
                    .withUsername(email)
                    .withUserPoolId(userPoolId);

            AdminGetUserResult result = awsCognitoIdentityProvider.adminGetUser(adminGetUserRequest);
            return result != null;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    public void confirmForgotPassword(ConfirmForgotPasswordDto confirmForgotPasswordDto) {
        ConfirmForgotPasswordRequest confirmForgotPasswordRequest = new ConfirmForgotPasswordRequest()
                .withClientId(cognitoClientId)
                .withUsername(confirmForgotPasswordDto.getEmail())
                .withConfirmationCode(confirmForgotPasswordDto.getConfirmationCode())
                .withPassword(confirmForgotPasswordDto.getPassword());
        awsCognitoIdentityProvider.confirmForgotPassword(confirmForgotPasswordRequest);
    }
}
