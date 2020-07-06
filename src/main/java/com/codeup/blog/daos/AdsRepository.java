package com.codeup.blog.daos;

import com.codeup.blog.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ad, Long> {
    // HQL
    @Query("from Ad as a where a.title like %:term% or a.description like %:term%")
    List<Ad> searchByTitle(@Param("term") String term);

    // query methods
    Ad findFirstByTitle(String title); // select * from ads where title = ? limit 1

    Ad findByTitle(String title);
}
