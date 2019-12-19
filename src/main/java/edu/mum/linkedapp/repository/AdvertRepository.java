package edu.mum.linkedapp.repository;

import edu.mum.linkedapp.domain.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert,Long> {
    @Query("Select MAX(a.id) from Advert a")
    Long findMaxId();
    @Query("select a from Advert a order by a.id desc")
    List<Advert> findAllOrdered();
    @Query(value = "SELECT * FROM Advert ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Advert> selectRandomAdverts();
}
