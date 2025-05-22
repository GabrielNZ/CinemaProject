package services;

import entities.DTO.MovieRoomDTO;
import entities.MovieRoom;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import repository.MovieRoomRepository;
import repository.MoviesRepository;
import services.exception.DataBaseException;
import services.exception.ResourceNotFound;

import java.util.List;

@Service
public class MovieRoomService {
    @Autowired
    private MovieRoomRepository movieRoomRepository;
    @Autowired
    private MoviesRepository moviesRepository;

    @Transactional
    public MovieRoom create(MovieRoomDTO movieRoom) {
        MovieRoom movieRoomEntity = new MovieRoom();
        movieRoomEntity.setAvailableSeats(movieRoom.availableSeats());
        return movieRoomRepository.save(movieRoomEntity);
    }

    public String registrateMovieRoom(Long movieRoomId, Long movieId) {
        MovieRoom movieRoom = movieRoomRepository.getById(movieRoomId);
        if(movieRoom.getMovie() != null) {
            movieRoom.setMovie(null);
            movieRoomRepository.save(movieRoom);
            return "The movie "+movieRoom.getMovie().getTitle()+" is not registered anymore";
        }
        movieRoom.setMovie(moviesRepository.getById(movieId));
        movieRoomRepository.save(movieRoom);
        return "Movie Room Registered Successfully with the movie: " + movieRoom.getMovie().getTitle();
    }

    public List<MovieRoom> findAll() {
        return movieRoomRepository.findAll();
    }

    public MovieRoom findById(Long id) {
        return movieRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Id not found: " + id));
    }

    @Transactional
    public MovieRoom update(MovieRoom movieRoom) {
        return movieRoomRepository.save(movieRoom);
    }

    @Transactional
    public void delete(Long id) {
        try {
            if (!movieRoomRepository.existsById(id)) {
                throw new ResourceNotFound("Id not found: " + id);
            }
            movieRoomRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
