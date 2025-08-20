package com.group2.factory_pattern.Service;

import com.group2.factory_pattern.DTO.AccountRequestDto;
import com.group2.factory_pattern.DTO.AccountResponseDto;

public interface AccountService {
    public AccountResponseDto createAccount(AccountRequestDto accountDto);
}
