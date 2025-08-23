package com.group2.factory_pattern.Service.Impl;

import com.group2.factory_pattern.DTO.AccountRequestDto;
import com.group2.factory_pattern.DTO.AccountResponseDto;
import com.group2.factory_pattern.Entity.Account;
import com.group2.factory_pattern.Mapper.AccountMapper;
import com.group2.factory_pattern.Repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountMapper accountMapper;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void createAccount_success() {
        AccountRequestDto dto = new AccountRequestDto("AC1", 500.0, 1000.0);
        Account entity = new Account("AC1", 500.0, 1000.0);
        AccountResponseDto response = new AccountResponseDto(1L, "AC1", 500.0, 1000.0);

        when(accountRepository.existsByAccountNumber("AC1")).thenReturn(false);
        when(accountMapper.toEntity(dto)).thenReturn(entity);
        when(accountRepository.save(entity)).thenReturn(entity);
        when(accountMapper.toDto(entity)).thenReturn(response);

        AccountResponseDto result = accountService.createAccount(dto);

        assertEquals("AC1", result.getAccountNumber());
        verify(accountRepository).save(entity);
    }

    @Test
    void createAccount_alreadyExists() {
        AccountRequestDto dto = new AccountRequestDto("AC1", 500.0, 1000.0);
        when(accountRepository.existsByAccountNumber("AC1")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> accountService.createAccount(dto));

        assertEquals("Account with this number already exists", ex.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void findAccount_success() {
        Account entity = new Account("AC1", 500.0, 1000.0);
        when(accountRepository.findByAccountNumber("AC1")).thenReturn(Optional.of(entity));
        assertEquals(entity, accountService.findByAccountNumber("AC1"));
    }

    @Test
    void findAccount_notFound() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> accountService.findByAccountNumber("AC1"));
        assertEquals("Account not found with number: AC1", ex.getMessage());
    }
}
