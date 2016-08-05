package shitstorm.pojos;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import shitstorm.interfaces.IGenericDAO;

public class GenericDAOImpl<T, PK extends Serializable> implements IGenericDAO<T, PK> {

	protected Class<T> entityClass;

	@PersistenceContext
	protected EntityManager em;

	public GenericDAOImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public T create(T t) {
		this.em.persist(t);
		return t;
	}

	@Override
	public T read(PK id) {
		return this.em.find(entityClass, id);
	}

	@Override
	public T update(T t) {
		return this.em.merge(t);
	}

	@Override
	public void delete(T t) {
		t = this.em.merge(t);
		this.em.remove(t);
	}
	
	@Override
	public List<T> readAll(){
		StringBuilder query = new StringBuilder();
		query.append(this.entityClass.getSimpleName());
		query.append(".findAll");
		return this.em.createNamedQuery(query.toString(),this.entityClass).getResultList();
	}
}
