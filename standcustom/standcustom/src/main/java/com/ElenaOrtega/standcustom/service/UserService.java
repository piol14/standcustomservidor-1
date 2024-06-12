package com.ElenaOrtega.standcustom.service;



import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ElenaOrtega.standcustom.entity.UserEntity;
import com.ElenaOrtega.standcustom.exception.ResourceNotFoundException;
import com.ElenaOrtega.standcustom.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;



@Service
public class UserService {

        private static final String STANDCUSTOMPASSWORD= "e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e";
      @Autowired
         private SessionService oSessionService;
        @Autowired
        UserRepository oUserRepository;

        @Autowired
        HttpServletRequest oHttpServletRequest;
    @Autowired
        private EmailService oEmailService;
 

    public UserEntity get(Long id) {
        oSessionService.onlyAdminsOrUsers();
        return oUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    public void sendEmail(UserEntity user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "https:/standcustom.es/initial/user/confirm-account?token=" + user.getToken());
        oEmailService.sendEmail(mailMessage);
    }
    public UserEntity getByEmail(String email) {
        return oUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by email"));
    }

     public UserEntity getByTokenPassword(String tokenPassword) {
        return oUserRepository.findByTokenPassword(tokenPassword)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by token"));
    }
    /*
     * Confirm email
     */
    

 public Page<UserEntity> getPage(Pageable pageable, String filter) {
    Page<UserEntity> page;

  
    if (filter == null || filter.isEmpty() || filter.trim().isEmpty()) {
      

       
        if (oSessionService.isAdmin()) {
      
            page = oUserRepository.findAll(pageable);
        } else {
       
            page = Page.empty();
        }
    } else {
     
        page = oUserRepository.findByUserByNameOrSurnameOrLastnameContainingIgnoreCase(
                filter, filter, filter, filter, pageable);
    }
  return page;
}

  


    

    /**
     * Send email to user with token
     * 
     * @param user
     */
 
    

    /*
     * Confirm email
     */
    public Long create(UserEntity oUserEntity) {
        oSessionService.onlyAdmins();
          oUserEntity.setId(null);
          oUserEntity.setPassword(STANDCUSTOMPASSWORD);
          return oUserRepository.save(oUserEntity).getId();
      }
      public UserEntity update(UserEntity updatedUserEntity) {
        UserEntity oUserEntityFromDatabase = this.get(updatedUserEntity.getId());
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oUserEntityFromDatabase.getId());
    
        if (oSessionService.isUser()) {
            // Ensure the role and password are not changed by a user
            updatedUserEntity.setRole(oUserEntityFromDatabase.getRole());
            updatedUserEntity.setPassword(oUserEntityFromDatabase.getPassword());
        } else {
            // Allow admins to change the role and reset the password to the default password
            updatedUserEntity.setPassword(STANDCUSTOMPASSWORD);
            oUserEntityFromDatabase.setRole(updatedUserEntity.getRole());
        }
    
        // Copy other fields to ensure only allowed fields are updated
        oUserEntityFromDatabase.setNombre(updatedUserEntity.getNombre());
        oUserEntityFromDatabase.setEmail(updatedUserEntity.getEmail());
        oUserEntityFromDatabase.setTelefono(updatedUserEntity.getTelefono());
        oUserEntityFromDatabase.setUsername(updatedUserEntity.getUsername());
    
        return oUserRepository.save(oUserEntityFromDatabase);
    }
    

    
    public Long delete(Long id) {
        oSessionService.onlyAdmins();
        oUserRepository.deleteById(id);
        return id;
    }
   
    public UserEntity getOneRandom() {
      oSessionService.onlyAdmins();
        Pageable oPageable = PageRequest.of((int) (Math.random() * oUserRepository.count()), 1);
        return oUserRepository.findAll(oPageable).getContent().get(0);
    }
   
   
public Long populate(Integer amount) {
    oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
                UserEntity usuario = new UserEntity();
                usuario.setNombre("usuario" + i);
                usuario.setEmail("email"+i+"@gmail.com");
                usuario.setTelefono("1234567" + i);
                usuario.setRole(false);
                usuario.setUsername("mitio"+i);
                usuario.setPassword(STANDCUSTOMPASSWORD);
                oUserRepository.save(usuario);
        }
        return amount.longValue();
}
public Long empty() {
  oSessionService.onlyAdmins();
    oUserRepository.deleteAll();
    oUserRepository.resetAutoIncrement();
    oUserRepository.flush();
    return oUserRepository.count();
}
   public UserEntity getByUsername(String username) {
        return oUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by username"));
    }
}