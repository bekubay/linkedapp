package edu.mum.linkedapp.service.impl;

import edu.mum.linkedapp.domain.Advert;
import edu.mum.linkedapp.repository.AdvertRepository;
import edu.mum.linkedapp.service.IAdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertService implements IAdvertService {
    @Autowired
    private AdvertRepository advertRepository;
    @Override
    public Advert save(Advert advert) {
        return advertRepository.save(advert);
    }

    @Override
    public Long findMaxId() {
        return advertRepository.findMaxId();
    }

    @Override
    public List<Advert> findAllOrdered() {
        return advertRepository.findAllOrdered();
    }
}
