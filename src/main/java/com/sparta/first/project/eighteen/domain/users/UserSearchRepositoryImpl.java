package com.sparta.first.project.eighteen.domain.users;

import static com.sparta.first.project.eighteen.model.users.QUsers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.first.project.eighteen.domain.users.dtos.AdminUserSearchRequestDto;
import com.sparta.first.project.eighteen.model.users.QUsers;
import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.SignUpType;
import com.sparta.first.project.eighteen.model.users.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSearchRepositoryImpl implements UserSearchRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Users> searchUser(AdminUserSearchRequestDto requestDto, Pageable pageable) {
		List<Users> users = queryFactory.selectFrom(QUsers.users)
			.where(
				dateBetween(requestDto.getDateStart(), requestDto.getDateEnd()),
				roleEquals(requestDto.getRole()),
				signUpTypeEquals(requestDto.getSignUpType()),
				roleExceptAdmin()
			)
			.orderBy(getAllOrderSpecifiers(pageable).toArray(new OrderSpecifier[] {}))
			.offset((long)requestDto.getPage() * requestDto.getSize())
			.limit(requestDto.getSize())
			.fetch();

		return new PageImpl<>(users, pageable, countUsers(requestDto));
	}

	/**
	 * 시작일 ~ 종료일 사이의 유저 검색
	 * @param dateStart : 조회하려는 시작일
	 * @param dateEnd : 조회하려는 종료일
	 * @return
	 */
	private BooleanExpression dateBetween(LocalDateTime dateStart, LocalDateTime dateEnd) {
		// 두 날짜 모두 있으면 사이 기간
		if (dateStart != null && dateEnd != null) {
			return users.createdAt.between(dateStart, dateEnd);
		}
		// 시작일만 있으면 시작일 이후
		if (dateStart != null) {
			return users.createdAt.after(dateStart);
		}
		// 종료일만 있으면 종료기간 전까지
		if (dateEnd != null) {
			return users.createdAt.before(dateEnd);
		}
		return null;
	}

	private BooleanExpression roleEquals(Role role) {
		return role != null ? users.role.eq(role) : null;
	}

	private BooleanExpression roleExceptAdmin() {
		return users.role.ne(Role.MASTER).and(
			users.role.ne(Role.MANAGER)
		);
	}

	private BooleanExpression signUpTypeEquals(SignUpType signUpType) {
		return signUpType != null ? users.signUpType.eq(signUpType) : null;
	}

	private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
		Sort sort = pageable.getSort();
		Order direction = sort.getOrderFor("createdAt").isAscending() ? Order.ASC : Order.DESC;
		return List.of(new OrderSpecifier<>(direction, users.createdAt));

	}

	private Long countUsers(AdminUserSearchRequestDto requestDto) {
		return queryFactory.select(users.count())
			.from(users)
			.where(
				roleEquals(requestDto.getRole()),
				signUpTypeEquals(requestDto.getSignUpType()),
				dateBetween(requestDto.getDateStart(), requestDto.getDateEnd())
			)
			.fetchOne();
	}
}
