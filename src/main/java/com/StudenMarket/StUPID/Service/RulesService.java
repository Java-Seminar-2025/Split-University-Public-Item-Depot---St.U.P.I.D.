package com.StudenMarket.StUPID.Service;

import com.StudenMarket.StUPID.Entity.Rules;
import com.StudenMarket.StUPID.Repository.RulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RulesService {
    @Autowired
    private RulesRepository rulesRepository;

    public List<Rules> ListAll()
    {
        return rulesRepository.findAll();
    }

    public Rules CreateRule(Rules rule)
    {
        return rulesRepository.save(rule);
    }
}
