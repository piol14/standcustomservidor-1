package com.ElenaOrtega.standcustom.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ElenaOrtega.standcustom.entity.OpinionEntity;
import com.ElenaOrtega.standcustom.service.OpinionService;

import org.springframework.data.domain.Sort;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/initial/opinion")

public class OpinionApi {
  private static final int PAGE_TAMANYO = 10;
    @Autowired
    OpinionService opinionService;

    @GetMapping("/{id}")
    public ResponseEntity<OpinionEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(opinionService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody OpinionEntity opinionEntity) {
        return ResponseEntity.ok(opinionService.create(opinionEntity));
    }

    @PutMapping("")
    public ResponseEntity<OpinionEntity> update(@RequestBody OpinionEntity opinionEntity) {
        return ResponseEntity.ok(opinionService.update(opinionEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(opinionService.delete(id));
    }
 @GetMapping("/byusuario/{id}")
    public ResponseEntity<Page<OpinionEntity>> getByUser(@PathVariable("id")  @PageableDefault(size = PAGE_TAMANYO, sort = {
        "id" }, direction = Sort.Direction.ASC) Long id, Pageable oPageable) {
        return ResponseEntity.ok(opinionService.getOpinionesByUser(id, oPageable));
    }

    @GetMapping("/bystand/{id}")
    public ResponseEntity<Page<OpinionEntity>> getByStand(@PathVariable("id")  @PageableDefault(size = PAGE_TAMANYO, sort = {
            "id" }, direction = Sort.Direction.ASC) Long id, Pageable oPageable) {
        return ResponseEntity.ok(opinionService.getOpinionesByStand(id, oPageable));
    }
    @GetMapping("")
    public ResponseEntity<Page<OpinionEntity>> getPage(
        Pageable oPageable,
        @RequestParam(value = "usuario", defaultValue = "0", required = false) Long userId,
        @RequestParam(value = "stand", defaultValue = "0", required = false) Long standId
        ) {
            return ResponseEntity.ok(opinionService.getPage(oPageable , userId, standId));
        }
      @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(opinionService.populate(amount));
    }
     @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(opinionService.empty());
    }
}
