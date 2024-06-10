package com.ElenaOrtega.standcustom.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ElenaOrtega.standcustom.entity.FavoritoEntity;

import com.ElenaOrtega.standcustom.repository.FavoritoRepository;

import org.springframework.data.domain.Pageable;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;
    @Autowired
    private StandService standService;

    public FavoritoEntity get(Long id) {
        return favoritoRepository.findById(id).orElse(null);
    }

  
    public Long create(FavoritoEntity favoritoEntity) {
        sessionService.onlyAdminsOrUsersWithIisOwnData(favoritoEntity.getUsuario().getId());
        favoritoRepository.save(favoritoEntity);
        return favoritoEntity.getId();
    }

   
    public FavoritoEntity update(FavoritoEntity updatedFavoritoEntity) {
        sessionService.onlyAdminsOrUsersWithIisOwnData(sessionService.getSessionUser().getId());
        return favoritoRepository.save(updatedFavoritoEntity);
    }

  
    public Long delete(Long id) {
        sessionService.onlyAdmins();
        favoritoRepository.deleteById(id);
        return id;
    }

    public Page<FavoritoEntity> getPage(Pageable pageable, Long userId, Long standId) {
        sessionService.onlyAdmins();
    
        if (userId == null || userId == 0) {
            if (standId == null || standId == 0) {
                return  favoritoRepository.findAll(pageable);
            } else {
                return  favoritoRepository.findByStandId(standId, pageable);
            }
        } else {
            return  favoritoRepository.findByUserId(userId, pageable);
        }
    }
    
 public Page<FavoritoEntity> getFavoritosByUser(Long id_usuario, Pageable oPageable) {
        return favoritoRepository.findByUserId(id_usuario, oPageable);
    }

    public Page<FavoritoEntity> getFavoritosByStand(Long id_producto, Pageable oPageable) {
        return favoritoRepository.findByStandId(id_producto, oPageable);
    }
    
    public FavoritoEntity getOneRandom() {
        sessionService.onlyAdmins();
        long count = favoritoRepository.count();
        int randomPage = (int) (Math.random() * count);
        return favoritoRepository.findAll(PageRequest.of(randomPage, 1)).getContent().get(0);
    }


    public Long populate(int amount) {
        sessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            FavoritoEntity favorito = new FavoritoEntity();
            favorito.setUsuario(userService.getOneRandom());
            favorito.setStand(standService.getOneRandom());
            favoritoRepository.save(favorito);
        }
        return (long) amount;
    }


    public Long empty() {
        sessionService.onlyAdmins();
        favoritoRepository.deleteAll();

        favoritoRepository.resetAutoIncrement();
        return favoritoRepository.count();
    }
    public boolean existeFavoritoRepetido(Long usuarioId, Long standId) {
        Page<FavoritoEntity> favoritos = favoritoRepository.findByUsuarioIdAndStandId(usuarioId, standId, Pageable.unpaged());
        return favoritos.getTotalElements() > 0;
    }
    
  
    public Optional<Long> obtenerFavoritoRepetidoId(Long usuarioId, Long standId) {
        Page<FavoritoEntity> favoritos = favoritoRepository.findByUsuarioIdAndStandId(usuarioId, standId, Pageable.unpaged());
        if (favoritos.getTotalElements() > 0) {
            FavoritoEntity favoritoRepetido = favoritos.getContent().get(0);
            return Optional.of(favoritoRepetido.getId());
        } else {
            return Optional.empty();
        }
    }
  
}
