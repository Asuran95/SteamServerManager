package steamservermanager.eao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import steamservermanager.models.ServerGame;

public class SteamServerManagerEAO extends AbstractEAO {
	
	public SteamServerManagerEAO(EntityManager entityManager) {
		super(entityManager);
	}

	public synchronized ServerGame persistServerGame(ServerGame serverGame) {
		persist(serverGame);

		return serverGame;
	}
	
	public synchronized ServerGame mergeServerGame(ServerGame serverGame) {
		merge(serverGame);
		
		return serverGame;
	}
	
	public synchronized List<ServerGame> findAllServerGame(){
		StringBuilder sb = new StringBuilder();
		sb.append(" select sg from ServerGame sg ");
		
		TypedQuery<ServerGame> query = createQuery(sb, ServerGame.class);

		return getResultList(query);
	}
	
	public ServerGame findServerGameById(Long idServerGame) {
		return find(ServerGame.class, idServerGame);
	}
	
	public ServerGame findServerGameByLocalName(String localName) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select sg from ServerGame sg ");
		sb.append(" where sg.localName = :localName ");
		
		TypedQuery<ServerGame> query = createQuery(sb, ServerGame.class);
		query.setParameter("localName", localName);
		
		return getSingleResult(query);
	}
}
