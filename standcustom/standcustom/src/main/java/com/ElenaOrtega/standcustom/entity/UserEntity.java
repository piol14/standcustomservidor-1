
package com.ElenaOrtega.standcustom.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Size(min = 3, max = 255)
    private String nombre;

 


    
   @Size(min = 7, max = 8)
@Pattern(regexp = "^[0-9]+$", message = "El telefono tiene que tiene que tener entre 7 y 8 numeros")
private String telefono;


    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "el usuario debe ser alfanumérico")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @NotBlank
    @Size(min = 64, max = 64)
    @Pattern(regexp = "^[a-fA-F0-9]+$", message = "la contraseña debe ser hexadecimal")
    private String password = "e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e";
    private String tokenPassword;

    private Boolean active = true;

    private boolean verified;

    private String token;


@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
private List<DetallePartidaEntity> detallePartida;
    @OneToMany(mappedBy = "usuario", fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.ALL)
    private List <OpinionEntity> opiniones;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
private List<StandEntity> stands;
@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
private List<FavoritoEntity> favoritos;
    private Boolean role = false;

    public UserEntity() {
              
        opiniones = new ArrayList<OpinionEntity>();
        stands = new ArrayList<StandEntity>();
        favoritos = new ArrayList<FavoritoEntity>();
        detallePartida = new ArrayList<DetallePartidaEntity>();
        
    }
 
    public UserEntity(Long id, String nombre, String email, String username,
            String password, Boolean role, String telefono, String token) {
        this.id = id;
        this.nombre = nombre;
        this.telefono= telefono;
    
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
      ;
        this.token = token;

    }

    public UserEntity(String nombre, String email, String username, String password,
            Boolean role) {
        this.nombre = nombre;
    
     
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

   public int getOpiniones() {
        return opiniones.size();
    }
 public  int getDetallePartida() {
    return detallePartida.size();
}

 public int getStands(){
    return stands.size();
 }
 public int getFavoritos(){
    return favoritos.size();
 }


public void setOpiniones(List<OpinionEntity> opiniones) {
    this.opiniones = opiniones;
}



public void setStands(List<StandEntity> stands) {
    this.stands = stands;
}



public void setFavoritos(List<FavoritoEntity> favoritos) {
    this.favoritos = favoritos;
}

public String getTokenPassword() {
    return tokenPassword;
}

public void setTokenPassword(String tokenPassword) {
    this.tokenPassword = tokenPassword;
}




public String getToken() {
    return token;
}

public void setToken(String token) {
    this.token = token;
}
}