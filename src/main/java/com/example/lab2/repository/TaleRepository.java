package com.example.lab2.repository;

import com.example.lab2.entity.Tale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaleRepository extends ListCrudRepository<Tale, Long> {
    @Query("SELECT t FROM Tale t ORDER BY SIZE(t.likedByUsers) DESC")
    Page<Tale> findBestTales(Pageable pageable);

    List<Tale> findFavoriteTalesByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT t FROM Tale t WHERE t NOT IN (SELECT tr FROM Tale tr JOIN tr.readByUsers ur WHERE ur.id = ?1)")
    List<Tale> findUnreadTalesByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM Tale t WHERE t.title LIKE %:criteria% OR t.author.name LIKE %:criteria%")
    List<Tale> findTaleByCriteria(@Param("criteria") String criteria);

}
