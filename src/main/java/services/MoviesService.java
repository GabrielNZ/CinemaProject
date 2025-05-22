package services;

import entities.MovieRoom;
import entities.Movies;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import repository.MoviesRepository;
import services.exception.DataBaseException;
import services.exception.ResourceNotFound;

import java.util.List;

@Service
public class MoviesService {
    @Autowired
    private MoviesRepository moviesRepository;
    @Autowired
    private MovieRoomService movieRoomService;

    @Transactional
    public Movies create(Movies movie) {
        return moviesRepository.save(movie);
    }

    public String registratingMovie(Long movieId, Long movieRoomId) {
        Movies movie = moviesRepository.findById(movieId).orElseThrow(() -> new ResourceNotFound("Movie not found"));
        MovieRoom movieRoom = movieRoomService.findById(movieRoomId);
        movie.getMovieRoom().add(movieRoom);
        return "The "+movie.getTitle()+" is now available in the Room "+movieRoom.getId();
    }

    public List<Movies> findAll() {
        return moviesRepository.findAll();
    }

    public Movies findById(Long id) {
        return moviesRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Id not found: "+id));
    }

    @Transactional
    public Movies update(Movies movie) {
        return moviesRepository.save(movie);
    }

    @Transactional
    public void delete(Long id) {
        try {
            if (!moviesRepository.existsById(id)) {
                throw new ResourceNotFound("Id not found: " + id);
            }
            moviesRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
