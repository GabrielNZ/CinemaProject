package repository;

import entities.MovieRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRoomRepository extends JpaRepository<MovieRoom, Long> {
}
