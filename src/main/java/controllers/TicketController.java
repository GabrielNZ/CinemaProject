package controllers;

import entities.MovieRoom;
import entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import services.MovieRoomService;
import services.TicketService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private MovieRoomService movieRoomService;

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ticketService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> findAll() {
        return ResponseEntity.ok().body(ticketService.findAll());
    }


    @PostMapping("{id}")
    public ResponseEntity<Object> createTicket(@RequestBody Ticket ticket,@PathVariable Long id) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ticket.getId()).toUri();
        if(movieRoomService.findById(id).getAvailableSeats() > 0) {
            ticket.setMovieRoom(movieRoomService.findById(id));
            ResponseEntity.created(uri).body(ticketService.create(ticket));
        }
        return ResponseEntity.ok().body("Movie Room already full");
    }

    @PostMapping("{ticketId}/{userId}")
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
