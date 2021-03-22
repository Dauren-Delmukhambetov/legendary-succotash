package by.itechart.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * Basic repository choosed because there could be some possibilities that
 * similar method with the same structures could exist, so there is no need to extend two or more times JpaRepository
 * and duplicate some methods there
 *
 * @param <T>  Entity
 * @param <ID> ID
 */
@NoRepositoryBean
public interface BasicRepository<T, ID> extends JpaRepository<T, ID> {

}
