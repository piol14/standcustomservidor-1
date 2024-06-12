package com.ElenaOrtega.standcustom.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ElenaOrtega.standcustom.entity.PartidaEntity;
import com.ElenaOrtega.standcustom.service.PartidaService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/initial/partida")
public class PartidaApi {

    @Autowired
    PartidaService partidaService;

    @GetMapping("/{id}")
    public ResponseEntity<PartidaEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(partidaService.get(id));
    }
    @GetMapping("/last")
    public Long getLastCreatedId() {
        return partidaService.getLastCreatedId();
    }
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody PartidaEntity partidaEntity) {
        return ResponseEntity.ok(partidaService.create(partidaEntity));
    }

    @PutMapping("")
    public ResponseEntity<PartidaEntity> update(@RequestBody PartidaEntity partidaEntity) {
        return ResponseEntity.ok(partidaService.update(partidaEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(partidaService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<PartidaEntity>> getPage(Pageable pageable)
            {
        return ResponseEntity.ok(partidaService.getPage(pageable));
    }
      @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(partidaService.populate(amount));
    }
     @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(partidaService.empty());
    }
}
