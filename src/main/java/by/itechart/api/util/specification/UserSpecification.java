package by.itechart.api.util.specification;

import by.itechart.api.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {
    private final String keyword;
    private boolean isOnlyActiveUsers = false;

    public UserSpecification(String keyword) {
        this.keyword = keyword;
    }

    public UserSpecification(String keyword, boolean onlyActiveUsers) {
        this.keyword = keyword;
        this.isOnlyActiveUsers = onlyActiveUsers;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<>();


        if (!ObjectUtils.isEmpty(keyword)) {
            predicates.add(cb.or(cb.like((root.get("firstName")), "%" + keyword + "%"),
                    cb.like(root.get("lastName"), "%" + keyword + "%"),
                    cb.like(root.get("email"), "%" + keyword + "%")));
        }
        if (isOnlyActiveUsers) {
            predicates.add(cb.isNull(root.get("deletedAt")));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
