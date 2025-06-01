package app;

import java.util.ArrayList;

import dominio.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meu-projeto-maven");
        EntityManager em = emf.createEntityManager();

        // Inicio do Bloco para criar uma nova pessoa
        for (int i = 0; i < 10; i++) {
            String nomeAleatorio = gerarNomeAleatorio(); 
            String emailAleatorio = "email" + nomeAleatorio.trim() + i + "@exemplo.com";
            createPessoa(em, nomeAleatorio, emailAleatorio);
        }

        // Listar todas as pessoas
        listarPessoas(em);

        // Remover uma pessoa pelo ID
        // removePessoa(em, 12);
 
        // Remover todas as pessoas
        // ArrayList<Integer> idsPessoas = new App().listarIdsPessoas(em);
        // for (Integer id : idsPessoas) {
        //     removePessoa(em, id);
        // }

        // Listar novamente para verificar a remoção
        listarPessoas(em);

        em.close();
        emf.close();

        // Fim do Bloco para criar uma nova pessoa
    }

    public static void listarPessoas(EntityManager em) {
        System.out.println("Listando todas as pessoas:");
        em.getTransaction().begin();
        java.util.List<Pessoa> pessoas = em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();
        for (Pessoa pessoa : pessoas) {
            System.out.println(pessoa);
        }
        em.getTransaction().commit();
    }



    public static void createPessoa(EntityManager em, String nome, String email) {
        Pessoa pessoa = new Pessoa(nome, email);
        em.getTransaction().begin();
        em.persist(pessoa);
        em.getTransaction().commit();
        System.out.println("Pessoa criada com sucesso: " + pessoa);
    }


    public static void removePessoa(EntityManager em, int id) {
        Pessoa pessoa = em.find(Pessoa.class, id);
        if (pessoa != null) {
            em.getTransaction().begin();
            em.remove(pessoa);
            em.getTransaction().commit();
            System.out.println("Pessoa removida com sucesso: " + pessoa);
        } else {
            System.out.println("Pessoa com ID " + id + " não encontrada.");
        }
    }

    public Number totalPessoas(EntityManager em) {
        em.getTransaction().begin();
        Number total = (Number) em.createQuery("SELECT COUNT(p) FROM Pessoa p").getSingleResult();
        em.getTransaction().commit();
        return total;
    }

    public ArrayList<Integer> listarIdsPessoas(EntityManager em) {
        em.getTransaction().begin();
        java.util.List<Integer> ids = em.createQuery("SELECT p.id FROM Pessoa p", Integer.class).getResultList();
        em.getTransaction().commit();
        return new ArrayList<>(ids);
    }

    public static String gerarNomeAleatorio() {
        String[] nomes = {"José", "Maria", "João", "Ana", "Carlos", "Paula", "Pedro", "Lucas", "Mariana", "Fernanda",
            "Roberto", "Juliana", "Ricardo", "Tatiane", "Eduardo", "Camila", "Gabriel", "Larissa",
            "Rafael", "Isabela"};
        String[] sobrenomes = {"Silva", "Souza", "Oliveira", "Santos", "Lima", "Costa", "Pereira", "Almeida", "Ferreira", "Rodrigues",
            "Martins", "Gomes", "Ribeiro", "Barbosa", "Carvalho", "Mendes", "Nogueira", "Pinto",
            "Teixeira", "Dias"};

        java.util.Random rand = new java.util.Random();
        String nome = nomes[rand.nextInt(nomes.length)];
        String sobrenome1 = sobrenomes[rand.nextInt(sobrenomes.length)];
        String sobrenome2 = sobrenomes[rand.nextInt(sobrenomes.length)];

        return nome + " " + sobrenome1 + " " + sobrenome2;
    }
}
