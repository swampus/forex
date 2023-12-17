package com.forex.homework.controller;

import com.forex.homework.dto.TransactionDTO;
import com.forex.homework.dto.TransferDTO;
import com.forex.homework.model.Transaction;
import com.forex.homework.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/history/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDTO> getTransactionHistory(
            @PathVariable String accountNumber,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {

        List<Transaction> transactions = transactionService.getTransactionHistory(accountNumber, offset, limit);
        return transactions.stream()
                .map(account -> modelMapper.map(account, TransactionDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/transfer")
    public void transferFunds(@RequestBody TransferDTO transferDTO) {
        transactionService.transferFunds(
                Encode.forJava(transferDTO.getSourceAccountNumber()),
                Encode.forJava(transferDTO.getTargetAccountNumber()),
                transferDTO.getAmount(),
                Encode.forJava(transferDTO.getCurrency())
        );
    }
}
