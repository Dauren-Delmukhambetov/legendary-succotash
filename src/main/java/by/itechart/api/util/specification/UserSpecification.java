package by.itechart.api.util.specification;

import by.itechart.api.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification implements Specification<User> {
    private final String keyword;

    public UserSpecification(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (ObjectUtils.isEmpty(keyword)) {
            return null;
        } else {
            return criteriaBuilder.or(criteriaBuilder.like((root.get("firstName")), "%" + keyword + "%"),
                    criteriaBuilder.like(root.get("lastName"), "%" + keyword + "%"),
                    criteriaBuilder.like(root.get("email"), "%" + keyword + "%"));
        }
    }
}
