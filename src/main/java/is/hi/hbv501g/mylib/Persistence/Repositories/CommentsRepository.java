package is.hi.hbv501g.mylib.Persistence.Repositories;

import is.hi.hbv501g.mylib.Persistence.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentsRepository extends JpaRepository<Comment, Integer> {
}
