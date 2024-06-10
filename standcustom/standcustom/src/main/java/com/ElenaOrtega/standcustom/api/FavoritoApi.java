package com.ElenaOrtega.standcustom.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ElenaOrtega.standcustom.entity.FavoritoEntity;
import com.ElenaOrtega.standcustom.service.FavoritoService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/favorito")
public class FavoritoApi {
    private static final int PAGE_TAMANYO = 10;
    @Autowired
    FavoritoService favoritoService;

    @GetMapping("/{id}")
    public ResponseEntity<FavoritoEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(favoritoService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody FavoritoEntity favoritoEntity) {
        return ResponseEntity.ok(favoritoService.create(favoritoEntity));
    }

    @PutMapping("")
    public ResponseEntity<FavoritoEntity> update(@RequestBody FavoritoEntity favoritoEntity) {
        return ResponseEntity.ok(favoritoService.update(favoritoEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(favoritoService.delete(id));
    }
    @GetMapping("/byusuario/{id}")
    public ResponseEntity<Page<FavoritoEntity>> getByUser(@PathVariable("id")  @PageableDefault(size = PAGE_TAMANYO, sort = {
        "id" }, direction = Sort.Direction.ASC) Long id, Pageable oPageable) {
        return ResponseEntity.ok(favoritoService.getFavoritosByUser(id, oPageable));
    }

    @GetMapping("/bystand/{id}")
    public ResponseEntity<Page<FavoritoEntity>> getByStand(@PathVariable("id")  @PageableDefault(size = PAGE_TAMANYO, sort = {
            "id" }, direction = Sort.Direction.ASC) Long id, Pageable oPageable) {
        return ResponseEntity.ok(favoritoService.getFavoritosByStand(id, oPageable));
    }
    @GetMapping("")
    public ResponseEntity<Page<FavoritoEntity>> getPage(
        
     
        Pageable oPageable,
        @RequestParam(value = "usuario", defaultValue = "0", required = false) Long userId,
        @RequestParam(value = "stand", defaultValue = "0", required = false) Long standId
        ) {
            return ResponseEntity.ok(favoritoService.getPage(oPageable , userId, standId));
        }
      @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(favoritoService.populate(amount));
    }
     @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(favoritoService.empty());
    }

    @GetMapping("/repetido/{usuarioId}/{standId}")
    public boolean existeFavoritoRepetido(@PathVariable Long usuarioId, @PathVariable Long standId) {
        return favoritoService.existeFavoritoRepetido(usuarioId, standId);
    }

    @GetMapping("/repetido/{usuarioId}/{standId}/id")
    public Optional<Long> obtenerFavoritoRepetidoId(@PathVariable Long usuarioId, @PathVariable Long standId) {
        return favoritoService.obtenerFavoritoRepetidoId(usuarioId, standId);
    }
}
