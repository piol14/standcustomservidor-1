package com.ElenaOrtega.standcustom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ElenaOrtega.standcustom.entity.PartidaEntity;
import com.ElenaOrtega.standcustom.repository.PartidaRepository;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final SessionService oSessionService;

    @Autowired
    public PartidaService(PartidaRepository partidaRepository, SessionService oSessionService) {
        this.partidaRepository = partidaRepository;
        this.oSessionService = oSessionService;
    }

    public PartidaEntity get(Long id) {
        return partidaRepository.findById(id).orElse(null);
    }

    public Long create(PartidaEntity partidaEntity) {
        partidaRepository.save(partidaEntity);
        return partidaEntity.getId();
    }

    public PartidaEntity update(PartidaEntity updatedPartidaEntity) {
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oSessionService.getSessionUser().getId());
        return partidaRepository.save(updatedPartidaEntity);
    }

    public Long delete(Long id) {
        oSessionService.onlyAdmins();
        partidaRepository.deleteById(id);
        return id;
    }

    public Page<PartidaEntity> getPage(Pageable pageable) {
        return partidaRepository.findAll(pageable);
    }

    public PartidaEntity getOneRandom() {
        oSessionService.onlyAdmins();
        Pageable oPageable = PageRequest.of((int) (Math.random() * partidaRepository.count()), 1);
        return partidaRepository.findAll(oPageable).getContent().get(0);
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            PartidaEntity partida = new PartidaEntity();
            partida.setFecha("18-02-2024");
            partidaRepository.save(partida);
        }
        return amount.longValue();
    }

    public Long empty() {
        oSessionService.onlyAdmins();
        partidaRepository.deleteAll();
        // Assuming resetAutoIncrement() and flush() are valid methods in your repository
        partidaRepository.resetAutoIncrement();
        partidaRepository.flush();
        return partidaRepository.count();
    }

    public Long getLastCreatedId() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<PartidaEntity> lastPartidaPage = partidaRepository.findAll(pageable);
        if (lastPartidaPage.hasContent()) {
            PartidaEntity lastPartida = lastPartidaPage.getContent().get(0);
            return lastPartida.getId();
        } else {
            return null;
        }
    }
}
