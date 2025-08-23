package com.group2.factory_pattern.Service.Impl;

import com.group2.factory_pattern.DTO.AccountRequestDto;
import com.group2.factory_pattern.DTO.AccountResponseDto;
import com.group2.factory_pattern.Entity.Account;
import com.group2.factory_pattern.Exception.PaymentException;
import com.group2.factory_pattern.Mapper.AccountMapper;
import com.group2.factory_pattern.Repository.AccountRepository;
import com.group2.factory_pattern.Service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;

    @Override
    public AccountResponseDto createAccount(AccountRequestDto accountDto) {
        if (accountRepository.existsByAccountNumber(accountDto.getAccountNumber())) {
            throw new RuntimeException("Account with this number already exists");
        }

        Account account = accountMapper.toEntity(accountDto);
        return accountMapper.toDto(accountRepository.save(account));

      }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new PaymentException("Account not found with number: " + accountNumber));
    }
}
