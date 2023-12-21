package com.sitrack.sitrackbackend.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.domain.account.QUserAccount.userAccount;

@Repository
@RequiredArgsConstructor
public class UserAccountRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<UserAccountDto> find(String searchType, String searchValue){

        List<UserAccountDto> userAccounts = queryFactory.select(userAccount)
                .where(typeCheck(searchType, searchValue))
                .from(userAccount)
                .fetch()
                .stream().map(UserAccountDto::from)
                .collect(Collectors.toList());

        return userAccounts;
    }

    private BooleanExpression userIdLike(String searchValue) {
        return StringUtils.hasText(searchValue) ? userAccount.userId.contains(searchValue) : null;
    }

    private BooleanExpression userNameLike(String searchValue) {
        return StringUtils.hasText(searchValue) ? userAccount.userName.contains(searchValue) : null;
    }

    private BooleanExpression userEmailLike(String searchValue) {
        return StringUtils.hasText(searchValue) ? userAccount.userEmail.contains(searchValue) : null;
    }

    private BooleanExpression typeCheck(String searchType, String searchValue){
        if(searchType.equals("userId")){
            return userIdLike(searchValue);
        }else if(searchType.equals("userName")){
            return userNameLike(searchValue);
        }else if(searchType.equals("userEmail")){
            return userEmailLike(searchValue);
        }
        return null;
    }
}
