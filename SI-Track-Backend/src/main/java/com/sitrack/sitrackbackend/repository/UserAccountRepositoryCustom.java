package com.sitrack.sitrackbackend.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.domain.account.QUserAccount.userAccount;

@Repository
@RequiredArgsConstructor
public class UserAccountRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<UserAccountDto> searchPageComplex(String searchType, String searchValue, Pageable pageable){
        List<UserAccountDto> content = getUserAccountDto(searchType, searchValue, pageable);
        Long count = getCount(searchType, searchValue);

        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(String searchType, String searchValue) {
        Long count = queryFactory
                .select(userAccount.count())
                .from(userAccount)
                .where(typeCheck(searchType, searchValue))
                .fetchOne();
        return count;
    }

    public List<UserAccountDto> getUserAccountDto(String searchType, String searchValue, Pageable pageable){

        List<UserAccountDto> userAccounts = queryFactory
                .select(userAccount)
                .from(userAccount)
                .where(typeCheck(searchType, searchValue))
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
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
        if(searchType == null){
            return null;
        }

        if(searchType.equals("userId")){
            return userIdLike(searchValue);
        }else if(searchType.equals("userName")){
            return userNameLike(searchValue);
        }else if(searchType.equals("userEmail")){
            return userEmailLike(searchValue);
        }else{
            return null;
        }
    }
}
