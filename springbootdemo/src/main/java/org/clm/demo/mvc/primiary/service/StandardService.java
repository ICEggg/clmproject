package org.clm.demo.mvc.primiary.service;

import lombok.extern.slf4j.Slf4j;
import org.clm.demo.mvc.primiary.compositekey.StandardPrimiaryKey;
import org.clm.demo.mvc.primiary.dao.StandardRepository;
import org.clm.demo.mvc.primiary.entity.Standard;
import org.clm.demo.mvc.primiary.serviceinterface.IStandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StandardService implements IStandService {

    @Autowired
    StandardRepository standardRepository;

    @Override
    public void addStandard(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    public void delStandard(String id, String version) {
        StandardPrimiaryKey key = new StandardPrimiaryKey();
        key.setId(id);
        key.setVersion(version);
        standardRepository.deleteById(key);
    }

    @Transactional
    @Override
    public void updateStandard(Standard standard) {
        //standardRepository.save(standard);
        standardRepository.updateStandard(standard.getName(),standard.getId());
    }

    @Override
    public Standard findStandardByIdVersion(String id, String version) {
        StandardPrimiaryKey key = new StandardPrimiaryKey();
        key.setId(id);
        key.setVersion(version);
        Optional<Standard> optStandard = standardRepository.findById(key);
        return optStandard.get();

    }

    @Override
    public List<Standard> findStandardByIdName(String id, String name) {
        return standardRepository.findStandardByIdName(id,name);
    }
}
