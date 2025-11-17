package is.hi.hbv501g.mylib.Persistence.Repositories;

import is.hi.hbv501g.mylib.Persistence.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/******************************************************************************
 * @author Emma Ófeigsdóttir
 * E-mail : emo16@hi.is
 * Description : Repository class for posts
 *
 *****************************************************************************/
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findPostById(int id);
}
