package entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_movieroom")
public class MovieRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long availableSeats;

    @OneToMany(mappedBy = "movieRoom")
    private List<Ticket> ticket;

    @ManyToOne
    @JoinColumn(name = "id_movie")
    private Movies movie;

    public Long getId() {
        return id;
    }

    public Long getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Long availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    public Movies getMovie() {
        return movie;
    }

    public void setMovie(Movies movie) {
        this.movie = movie;
    }
}
