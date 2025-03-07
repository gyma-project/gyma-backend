package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TransactionDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.TransactionMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.Transaction;
import com.gyma.gyma.model.enums.CategoryTransaction;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TransactionRepository;
import com.gyma.gyma.specificiations.TransactionSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionDTO transactionDTO;
    private Transaction transaction;
    private Profile createdBy;
    private Profile updateBy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Definir UUID fixo para os perfis
        createdBy = new Profile();
        createdBy.setKeycloakId(UUID.fromString("1015354e-0042-4740-a784-197d96243171"));

        updateBy = new Profile();
        updateBy.setKeycloakId(UUID.fromString("74ca361f-9f06-4c5d-afdb-a1c71ee3d0f3"));

        // Criando o DTO com todos os parâmetros necessários
        transactionDTO = new TransactionDTO(
                createdBy.getKeycloakId(),  // createdById
                updateBy.getKeycloakId(),   // updateById
                BigDecimal.valueOf(150.00), // price
                "Mensalidade da academia",  // description
                "MEMBERSHIP"                // category
        );

        // Criando uma instância de Transaction para testes
        transaction = new Transaction();
        transaction.setId(1);
        transaction.setCreatedBy(createdBy);
        transaction.setUpdateBy(updateBy);
    }


    @Test
    void create_ShouldCreateTransaction_WhenValidDTO() {
        // Arrange
        when(profileRepository.findByKeycloakId(transactionDTO.createdById())).thenReturn(Optional.of(createdBy));
        when(profileRepository.findByKeycloakId(transactionDTO.updateById())).thenReturn(Optional.of(updateBy));
        when(transactionMapper.toEntity(transactionDTO)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Act
        Transaction createdTransaction = transactionService.create(transactionDTO);

        // Assert
        assertNotNull(createdTransaction);
        assertEquals(transaction.getId(), createdTransaction.getId());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void create_ShouldThrowResourceNotFoundException_WhenProfileNotFound() {
        // Arrange
        when(profileRepository.findByKeycloakId(transactionDTO.createdById())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> transactionService.create(transactionDTO));
    }

    @Test
    void getAllTransactions_ShouldReturnPageOfTransactions_WhenValidParams() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> transactions = List.of(transaction1, transaction2);
        Page<Transaction> transactionPage = new PageImpl<>(transactions, pageRequest, transactions.size());

        when(transactionRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(transactionPage);

        // Act
        Page<Transaction> result = transactionService.getAllTransactions(
                UUID.randomUUID(), BigDecimal.ZERO, BigDecimal.TEN, "Test", LocalDate.now(), LocalDate.now(), CategoryTransaction.MEMBERSHIP, 0, 10
        );

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void getTransactionById_ShouldReturnTransaction_WhenExists() {
        // Arrange
        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        // Act
        Transaction foundTransaction = transactionService.getTransactionById(1);

        // Assert
        assertNotNull(foundTransaction);
        assertEquals(transaction.getId(), foundTransaction.getId());
    }

    @Test
    void getTransactionById_ShouldThrowResourceNotFoundException_WhenNotExists() {
        // Arrange
        when(transactionRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> transactionService.getTransactionById(1));
    }


    @Test
    void deleteTransaction_ShouldDeleteTransaction_WhenExists() {
        // Arrange
        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        // Act
        transactionService.deleteTransaction(1);

        // Assert
        verify(transactionRepository, times(1)).delete(transaction);
    }

    @Test
    void deleteTransaction_ShouldThrowResourceNotFoundException_WhenNotExists() {
        // Arrange
        when(transactionRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> transactionService.deleteTransaction(1));
    }
}
