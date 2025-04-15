package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MovieRoomRepository;

@Service
public class MovieRoomService {
    @Autowired
    private MovieRoomRepository movieRoomRepository;
}
