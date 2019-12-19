package edu.mum.linkedapp.service;

import edu.mum.linkedapp.domain.Advert;
import edu.mum.linkedapp.domain.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IAdvertService {
    Advert save(Advert advert);
    Long findMaxId();
    List<Advert> findAllOrdered();
}
