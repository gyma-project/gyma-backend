package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TransactionDTO;
import com.gyma.gyma.controller.specificiations.TransactionSpecification;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.TransactionMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.Transaction;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private ProfileRepository profileRepository;

    public Transaction create(TransactionDTO transactionDTO){
        Transaction transaction = transactionMapper.toEntity(transactionDTO);

        Profile sender = profileRepository.findByKeycloakId(transactionDTO.senderId())
                .orElseThrow(() -> new ResourceNotFoundException("Pagador não encontrado por UUID."));

        Profile createdBy = profileRepository.findByKeycloakId(transactionDTO.createdById())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Perfil que criou transação não encontrado por UUID."
                ));

        Profile updateBy = profileRepository.findByKeycloakId(transactionDTO.updateById())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Perfil que tentou atualizar não foi encontrado por UUID."
                ));

        transaction.setSender(sender);
        transaction.setCreatedBy(createdBy);
        transaction.setUpdateBy(updateBy);

        return transactionRepository.save(transaction);
    }

    public Page<Transaction> getAllTransactions(
            UUID senderId,
            UUID createdById,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String description,
            LocalDate startDate,
            LocalDate updateAt,
            Integer pageNumber,
            Integer size
    ){

        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (size == null) {
            size = 10;
        }

        Pageable page = PageRequest.of(pageNumber, size);

        Specification<Transaction> spec = Specification.where(
                TransactionSpecification.bySenderId(senderId)
                .and(TransactionSpecification.byCreatedById(createdById))
                .and(TransactionSpecification.byPriceLessThan(maxPrice))
                .and(TransactionSpecification.byPriceGreaterThan(minPrice))
                .and(TransactionSpecification.byDescription(description))
                .and(TransactionSpecification.byCreatedAt(startDate))
                .and(TransactionSpecification.byUpdatedAt(updateAt))
        );

        return transactionRepository.findAll(spec, page);
    }

    public Transaction getTransactionById(
            Integer id
    ){
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado pelo id: " + id));
    }

    public Transaction updateTransaction(
            Integer id,
            TransactionDTO transactionDTO
    ) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado pelo id: " + id));

        transactionMapper.updateEntityFromDTO(transactionDTO, existingTransaction);
        return transactionRepository.save(existingTransaction);
    }

    public void deleteTransaction(Integer id){
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado pelo id: " + id));
        transactionRepository.delete(existingTransaction);
    }

}
