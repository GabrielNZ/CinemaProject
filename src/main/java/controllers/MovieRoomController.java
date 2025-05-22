package controllers;

import entities.DTO.MovieRoomDTO;
import entities.MovieRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.MovieRoomService;

import java.util.List;

@RestController
@RequestMapping("/moviesroom")
public class MovieRoomController {
    @Autowired
    private MovieRoomService movieRoomService;

    @GetMapping("/{id}")
    public ResponseEntity<MovieRoom> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(movieRoomService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<MovieRoom>> findAll() {
        return ResponseEntity.ok().body(movieRoomService.findAll());
    }

    @PostMapping
    public ResponseEntity<MovieRoom> createMovie(@RequestBody MovieRoomDTO movieRoom) {
        return ResponseEntity.ok().body(movieRoomService.create(movieRoom));
    }

    @PutMapping
    public ResponseEntity<MovieRoom> updateMovieRoom(@RequestBody MovieRoom movieRoom) {
        return ResponseEntity.ok().body(movieRoomService.update(movieRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        movieRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
