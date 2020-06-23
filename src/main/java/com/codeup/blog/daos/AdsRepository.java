package com.codeup.blog.daos;

import com.codeup.blog.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdsRepository extends JpaRepository<Ad, Long> {

}
