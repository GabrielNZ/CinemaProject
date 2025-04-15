package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MoviesRepository;

@Service
public class MoviesService {
    @Autowired
    private MoviesRepository moviesRepository;
}
