package shitstorm.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import shitstorm.interfaces.INodeDAO;
import shitstorm.persistence.entities.ENode;
import shitstorm.pojos.GenericDAOImpl;

@Stateless
@Local(INodeDAO.class)
public class NodeDAOBean extends GenericDAOImpl<ENode, Integer> implements INodeDAO {

}
