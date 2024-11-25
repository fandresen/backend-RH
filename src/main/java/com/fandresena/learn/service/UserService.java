package com.fandresena.learn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fandresena.learn.DTO.UserDTO;
import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.AdminModel;
import com.fandresena.learn.model.UserModel;

import lombok.AllArgsConstructor;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserDAO userDAO;
    private BCryptPasswordEncoder passwordEncoder;
    NewPasswordTokenService newPasswordTokenService;
    JWTService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<UserModel> getAllUsers() {
        return userDAO.getAllUsersByRole("USER");
    }

    public UserModel getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public UserModel createUser(UserModel userModel) {
        String password = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(password);
        UserModel userModel1 = userDAO.createUser(userModel);
        return userModel1;
    }

    public UserModel createEntireuser(UserModel user, String token) {
        try {
            int entrepriseId = jwtService.extractEntrepriseId(token);
            user.setEntreprise_id(entrepriseId);

            UserModel userModel = this.createUser(user);
            logger.info("create user" + userModel.getEmail());
            logger.info("create user ID" + userModel.getId());

            // Generate newPasswordToken
            String tokenPassword = TokenGeneratorService.generatepassword(12);
            String realToken = newPasswordTokenService.createToken(userModel, tokenPassword);
            String template = new String(
                    Files.readAllBytes(Paths.get("src/main/resources/templates/CreateNewPassword.html")));
            SendEmailService.sendEmail(user.getEmail(), "Compte ZenRH", user.getFirst_name(),
                    " http://192.168.1.242:5173/newPassword?tkn=" + realToken, template);

            return userModel;
        } catch (Exception e) {
            logger.error("CREATE ENTIRE USER ERROR : " + e.getMessage());
            return null;
        }
    }

    public void createAdmin(AdminModel adminModel) {
        String password = passwordEncoder.encode(adminModel.getPassword());
        adminModel.setPassword(password);
        userDAO.createAdmin(adminModel);
    }

    public List<UserModel> getByDepartementId(int id) {
        List<UserModel> users = userDAO.getAllUserByDepartementId(id);
        return users;
    }

    public List<UserModel> getAllByEntrepriseId(int entrepriseId) {
        List<UserModel> users = userDAO.getAllUsersByRole("USER");
        List<UserModel> entrepriseUsers = users.stream().filter(user -> user.getEntreprise_id() == entrepriseId)
                .collect(Collectors.toList());
        return entrepriseUsers;
    }

    public void changePassword(UserModel user, String password) {
        UserModel newuser = userDAO.findByEmail(user.getEmail());
        if (newuser != null) {
            String encodedPassword = passwordEncoder.encode(password);
            newuser.setPassword(encodedPassword);
            userDAO.updateUser(newuser);
        }
    }

    public UserDTO convertToDTO(UserModel user) {
        return new UserDTO(user.getId(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPicture());
    }
}
