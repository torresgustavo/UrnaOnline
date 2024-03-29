package Repository;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.Order;

import Model.Candidatos;
import Model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class CandidatosRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("UrnaPersistence");
    private EntityManager em = emf.createEntityManager();
    
    public List<Candidatos> buscaTodosCandidatos() {		
        try {
            Query query = em.createQuery("select c from Candidatos c ORDER BY c.votos desc");
            List<Candidatos> candidatos = query.getResultList();
        
            return candidatos; 
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public Candidatos buscaCandidatosNumero(int numero) {
        try {
            Query query = em.createQuery("select c from Candidatos c where c.numero = :numero");
            query.setParameter("numero", numero);
            Candidatos candidato = (Candidatos) query.getSingleResult();
            

            return candidato;
        } catch (Exception e) {
            //TODO: handle exception
            return null;
        } 
    }

    public void SomarVotacao (int numero){
        Candidatos candidato = buscaCandidatosNumero(numero);
        int x = candidato.getVotos();
        candidato.setVotos(x+1);

        em.getTransaction().begin();
        try {
            em.merge(candidato);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

    }
}