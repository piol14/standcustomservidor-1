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

import org.springframework.web.bind.annotation.RestController;

import com.ElenaOrtega.standcustom.entity.CategoriaEntity;
import com.ElenaOrtega.standcustom.service.CategoriaService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/initial/categoria")
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
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaEntity> getByNombre(@PathVariable("nombre") String nombre) {
        CategoriaEntity categoria = categoriaService.findByNombre(nombre);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoriaService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<CategoriaEntity>> getPage(
            Pageable pageable
           
       ) {
        return ResponseEntity.ok(categoriaService.getPage(pageable));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(categoriaService.populate(amount));
    }
    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(categoriaService.empty());
    }
}
