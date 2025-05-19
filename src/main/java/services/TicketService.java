package services;

import entities.Ticket;
import entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import repository.TicketRepository;
import services.exception.DataBaseException;
import services.exception.ResourceNotFound;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
    public String registratingTicket(Long ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFound("Ticket not found"));
        User user = userService.findById(userId);
        ticket.setUser(user);
        user.getTicket().add(ticket);
        delete(ticketId);
        return "User: "+user.getUsername()+" is now owning the ticket from id: "+ticketId;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Id not found: "+id));
    }

    @Transactional
    public Ticket update(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Transactional
    public void delete(Long id) {
        try {
            if (!ticketRepository.existsById(id)) {
                throw new ResourceNotFound("Id not found: " + id);
            }
            ticketRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
