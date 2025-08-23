package com.group2.factory_pattern.Service;

import com.group2.factory_pattern.DTO.AccountRequestDto;
import com.group2.factory_pattern.DTO.AccountResponseDto;
import com.group2.factory_pattern.Entity.Account;

public interface AccountService {
    public AccountResponseDto createAccount(AccountRequestDto accountDto);
    public Account findByAccountNumber (String accountNumber);
}
