package steamservermanager.eao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public abstract class AbstractEAO {
	
	private EntityManager entityManager;

	public AbstractEAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	protected <T> void persist(T entity) {
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}
	
	protected <T> void merge(T entity) {
		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
	}
	
	protected <T> T find(Class<T> entityClass, Object primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}
	
	protected <T> TypedQuery<T> createQuery(StringBuilder sb, Class<T> resultClass){
		return entityManager.createQuery(sb.toString(), resultClass);
	}
	
	protected <T> T getSingleResult(TypedQuery<T> query) {
		try {
			return (T) query.getSingleResult();
		} catch(NoResultException ex){
			return null;
		}
	}
	
	protected <T> List<T> getResultList(TypedQuery<T> query) {
		return query.getResultList();
	}

}
