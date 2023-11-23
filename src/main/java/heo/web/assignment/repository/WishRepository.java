package heo.web.assignment.repository;

import heo.web.assignment.entity.Member;
import heo.web.assignment.entity.Product;
import heo.web.assignment.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberAndProduct(Member member, Product product);

}
