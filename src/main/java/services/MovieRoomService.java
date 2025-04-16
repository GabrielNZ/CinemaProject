package services;

import entities.MovieRoom;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import repository.MovieRoomRepository;
import services.exception.DataBaseException;
import services.exception.ResourceNotFound;

import java.util.List;

@Service
public class MovieRoomService {
    @Autowired
    private MovieRoomRepository movieRoomRepository;

    @Transactional
    public MovieRoom create(MovieRoom movieRoom) {
        return movieRoomRepository.save(movieRoom);
    }

    public List<MovieRoom> findAll() {
        return movieRoomRepository.findAll();
    }

    public MovieRoom findById(Long id) {
        return movieRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Id not found: "+id));
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
