package by.itechart.api.util.specification;

import by.itechart.api.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
            return criteriaBuilder.like(root.get("firstName"), "%" + keyword + "%");
        }
    }


    //FIXME not working
    private Map<String, String> attributeMap(String keyword) {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("firstName", keyword);
        map.put("lastName", keyword);
        map.put("email", keyword);
        return map;
    }
}
