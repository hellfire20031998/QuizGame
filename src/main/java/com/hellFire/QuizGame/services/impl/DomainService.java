package com.hellFire.QuizGame.services.impl;

import com.hellFire.QuizGame.dto.DomainDto;
import com.hellFire.QuizGame.dto.request.CreateDomainRequest;
import com.hellFire.QuizGame.entity.Domain;
import com.hellFire.QuizGame.mapper.IDomainMapper;
import com.hellFire.QuizGame.repositories.IDomainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DomainService {

    private final IDomainRepository domainRepository;
    private final IDomainMapper domainMapper;

    public DomainService(IDomainRepository domainRepository, IDomainMapper domainMapper) {
        this.domainRepository = domainRepository;
        this.domainMapper = domainMapper;
    }

    public Domain createDomain(CreateDomainRequest request) {

        String name = request.getName();
        if (domainRepository.existsByName(name)) {
            throw new RuntimeException("Domain already exists");
        }

        Domain domain = new Domain();
        domain.setName(name);

        return domainRepository.save(domain);
    }

    public List<Domain> getAllDomains() {
        return domainRepository.findAll();
    }

    public Domain getDomainById(Long id) {
        return domainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Domain not found"));
    }

    public List<Domain> getDomainByIds(List<Long> ids) {
        return domainRepository.findAllById(ids);
    }

    public Domain updateDomain(Long id, String newName) {

        Domain domain = getDomainById(id);

        if (!domain.getName().equals(newName)
                && domainRepository.existsByName(newName)) {
            throw new RuntimeException("Domain name already exists");
        }

        domain.setName(newName);

        return domainRepository.save(domain);
    }

    public void deleteDomain(Long id) {
        Domain domain = getDomainById(id);
        domainRepository.delete(domain);
    }

    public List<Domain> getLatestDomains() {
        return domainRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public List<DomainDto> getRelatedDomains(String name) {

        if (name == null || name.trim().length() < 3) {
            throw new IllegalArgumentException("Search query must contain at least 3 characters");
        }

        List<Domain> domains =
                domainRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name.trim());

        return domainMapper.toDtoList(domains);
    }

    public List<Domain> getRelatedDomains(Long domainId) {
        return domainRepository.findTop5ByIdNotOrderByCreatedAtDesc(domainId);
    }

    public List<Domain> getDomainsByIds(List<Long> ids){
        return domainRepository.findAllById(ids);
    }

    public List<DomainDto> toDtoList(List<Domain> domains){
        return domainMapper.toDtoList(domains);
    }

    public DomainDto toDto(Domain domain){
        return domainMapper.toDto(domain);
    }
}
