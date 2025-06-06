package controllers;

import entities.MovieRoom;
import entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import repository.MovieRoomRepository;
import services.TicketService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private MovieRoomRepository movieRoomRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ticketService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> findAll() {
        return ResponseEntity.ok().body(ticketService.findAll());
    }


    @PostMapping("/create/{id}")
    public ResponseEntity<Object> createTicket(@RequestBody Ticket ticket,@PathVariable Long movieRoomId) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ticket.getId()).toUri();
        MovieRoom movieRoom = movieRoomRepository.findById(movieRoomId).get();
        if(movieRoom.getAvailableSeats() > 0) {
            ticket.setMovieRoom(movieRoom);
            movieRoom.getTicket().add(ticket);
            movieRoomRepository.save(movieRoom);
            return ResponseEntity.created(uri).body(ticketService.create(ticket));
        }
        return ResponseEntity.ok().body("Movie Room already full");
    }

    @PostMapping("/registrate/{ticketId}/{userId}")
    public ResponseEntity<Ticket> registrateTicket(@PathVariable Long ticketId,@PathVariable Long userId) {
        ticketService.registratingTicket(ticketId,userId);
        return ResponseEntity.ok().body(ticketService.findById(ticketId));
    }

    @PutMapping
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok().body(ticketService.update(ticket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
