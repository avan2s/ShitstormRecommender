package recommender.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO <T, PK extends Serializable> {
	public T create(T t);

	public T read(PK id);

	public T update(T t);

	public void delete(T t);
	
	public List<T> readAll();
}
