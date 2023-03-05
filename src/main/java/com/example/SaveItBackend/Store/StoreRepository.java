package com.example.SaveItBackend.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s WHERE s.email = ?1")
    Optional<Store> findStoreByEmail(String email);

    @Query("SELECT MAX(s.id) FROM Store s")
    Integer getNumberOfStores();

}
