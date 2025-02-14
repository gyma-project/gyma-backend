package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.TransactionDTO;
import com.gyma.gyma.model.Transaction;
import com.gyma.gyma.model.enums.CategoryTransaction;
import com.gyma.gyma.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("transactions")
@Tag(name = "Transações financeiras", description = "Gerenciamento de transações.")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Criar uma nova transação", description = "Cria uma nova transação financeira.")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.create(transactionDTO));
    }

    @GetMapping
    @Operation(
            summary = "Listar todas as transações",
            description = "Retorna uma lista de todas as transações financeiras."
    )
    public Page<Transaction> getAllTransactions(
            @RequestParam(required = false) UUID createdById,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate updateAt,
            @RequestParam(required = false) CategoryTransaction category,
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        return transactionService.getAllTransactions(
                createdById,
                minPrice,
                maxPrice,
                description,
                startDate,
                updateAt,
                category,
                pageNumber,
                size
                );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obter uma transação por ID",
            description = "Retorna uma transação financeira específica pelo seu ID."
    )
    public ResponseEntity<Transaction> getTransactionById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma transação", description = "Atualiza uma transação financeira existente.")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Integer id,
            @RequestBody TransactionDTO updatedTransaction
    ) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, updatedTransaction));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma transação", description = "Deleta uma transação financeira existente.")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Integer id
    ) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

}
