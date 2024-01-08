package com.ElenaOrtega.standcustom.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuario_stand")
public class UsuarioStandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_usuario")
    private UserEntity usuario;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_stand")
    private StandEntity stand;

    // Constructores, getters y setters

    public UsuarioStandEntity() {
    }

    public UsuarioStandEntity(UserEntity usuario, StandEntity stand) {
        this.usuario = usuario;
        this.stand = stand;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UserEntity usuario) {
        this.usuario = usuario;
    }

    public StandEntity getStand() {
        return stand;
    }

    public void setStand(StandEntity stand) {
        this.stand = stand;
    }
}
