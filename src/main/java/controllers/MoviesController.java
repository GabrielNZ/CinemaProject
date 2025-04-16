package controllers;

import entities.Movies;
import entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import services.MoviesService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movies")

public class MoviesController {
    @Autowired
    private MoviesService moviesService;

    @GetMapping("/{id}")
    public ResponseEntity<Movies> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(moviesService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Movies>> findAll() {
        return ResponseEntity.ok().body(moviesService.findAll());
    }

    @PostMapping
    public ResponseEntity<Movies> createMovie(@RequestBody Movies movies) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(movies.getId()).toUri();
        return ResponseEntity.created(uri).body(moviesService.create(movies));
    }

    @PutMapping
    public ResponseEntity<Movies> updateMovie(@RequestBody Movies movies) {
        return ResponseEntity.ok().body(moviesService.update(movies));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        moviesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
