package steamservermanager.eao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public abstract class AbstractEAO<T> {
	
	private EntityManager entityManager = EntityManagerSingleton.getEntityManager();
	
	private Class<T> entityClass;
	
	public AbstractEAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public synchronized void persist(T entity) {
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}
	
	public synchronized void merge(T entity) {
		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
	}
	
	public T find(Object primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}
	
	protected TypedQuery<T> createQuery(StringBuilder sb, Class<T> resultClass){
		return entityManager.createQuery(sb.toString(), resultClass);
	}
	
	protected T getSingleResult(TypedQuery<T> query) {
		try {
			return (T) query.getSingleResult();
		} catch(NoResultException ex){
			return null;
		}
	}
	
	protected List<T> getResultList(TypedQuery<T> query) {
		return query.getResultList();
	}

}
