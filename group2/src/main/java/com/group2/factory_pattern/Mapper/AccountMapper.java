package com.group2.factory_pattern.Mapper;
import com.group2.factory_pattern.DTO.*;
import com.group2.factory_pattern.Entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponseDto toDto(Account account);

    Account toEntity(AccountRequestDto dto);
}
