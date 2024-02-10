package com.ElenaOrtega.standcustom.service;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.ElenaOrtega.standcustom.entity.StandEntity;
import com.ElenaOrtega.standcustom.entity.UserEntity;
import com.ElenaOrtega.standcustom.helper.StandPopulator;
import com.ElenaOrtega.standcustom.repository.StandRepository;

@Service
public class StandService {
    @Autowired
    private SessionService oSessionService;
    @Autowired
    private StandRepository standRepository;
     @Autowired
    private StandPopulator standPopulator;
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UserService userService;
    public StandEntity get(Long id) {
     
        return standRepository.findById(id).orElse(null);
    }

    public Long create(StandEntity standEntity) {
        oSessionService.onlyAdminsOrUsers();
        standRepository.save(standEntity);
        return standEntity.getId();
    }

    public StandEntity update(StandEntity standEntity) {
           StandEntity oStandEntityFromDatabase = this.get(standEntity.getId());
    oSessionService.onlyAdminsOrUsersWithIisOwnData(oStandEntityFromDatabase.getUsuario().getId());
        return standRepository.save(standEntity);
    }

    public Long delete(Long id) {
          StandEntity oStandEntityFromDatabase = this.get(id);
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oStandEntityFromDatabase.getUsuario().getId());
        standRepository.deleteById(id);
        return id;
    }

    public Page<StandEntity> getPage(Pageable pageable, Long usuario) {
        if (usuario != null && usuario != 0) {
            return standRepository.findByUsuarioId(usuario, pageable);
        }
   public Page<StandEntity> getPage(Pageable pageable, String strFilter) {
  
    
        return standRepository.findAll(pageable);
   
}

public StandEntity getOneRandom() {
    oSessionService.onlyAdmins();
        Pageable oPageable = PageRequest.of((int) (Math.random() * standRepository.count()), 1);
        return standRepository.findAll(oPageable).getContent().get(0);
    }

 public Long populate(Integer amount) {
   oSessionService.onlyAdmins();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    for (int i = 0; i < amount; i++) {
        StandEntity stand = new StandEntity();

        // Crear un ataque stand ficticio
       
        // Otros datos del stand
        stand.setNombre("Nombre del Stand " + i);
        stand.setDescripcion("Descripción del Stand " + i);
        stand.setImagen("http://localhost:8083/media/hermitpurple.png");
        stand.setVelocidad("A");
        stand.setAlcance("B");
        stand.setPoder("A");
        stand.setDesarollo("D");
        stand.setAcierto("C");
        stand.setAguante("B");

        stand.setCategoria(categoriaService.getOneRandom());

        // Establecer la relación entre el stand y el ataque stand
        
        stand.setUsuario(userService.getOneRandom());
        // Guardar el stand en la base de datos
        standRepository.save(stand);
    }
    return amount.longValue();
}
 public Long populateStandsFromJson(String jsonFilePath) {
        // Agregar lógica adicional si es necesario antes o después de poblar desde el JSON
        return standPopulator.populateFromJson(jsonFilePath);
    }

    public Long empty() {
         oSessionService.onlyAdmins();
        long countBeforeDeletion = standRepository.count();
        standRepository.deleteAll();
        long countAfterDeletion = standRepository.count();
        return countBeforeDeletion - countAfterDeletion;
    }



}
