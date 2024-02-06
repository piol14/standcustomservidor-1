package com.ElenaOrtega.standcustom.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.ElenaOrtega.standcustom.entity.CategoriaEntity;
import com.ElenaOrtega.standcustom.entity.CategoriaEntity;
import com.ElenaOrtega.standcustom.service.CategoriaService;
import com.ElenaOrtega.standcustom.service.DetallePartidaService;
    
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/categoria")
public class CategoriaApi {



    @Autowired
  CategoriaService categoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoriaService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody CategoriaEntity categoriaEntity) {
        return ResponseEntity.ok(categoriaService.create(categoriaEntity));
    }

    @PutMapping("")
    public ResponseEntity<CategoriaEntity> update(@RequestBody CategoriaEntity categoriaEntity) {
        return ResponseEntity.ok(categoriaService.update(categoriaEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoriaService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<CategoriaEntity>> getPage(
            Pageable pageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return ResponseEntity.ok(categoriaService.getPage(pageable, strFilter));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(categoriaService.populate(amount));
    }
}
