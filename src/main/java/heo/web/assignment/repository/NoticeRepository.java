package heo.web.assignment.repository;

import heo.web.assignment.entity.Inquiry;
import heo.web.assignment.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {

}
