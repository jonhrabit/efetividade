package br.com.efetividade.excepions;


public class ItemNotFoundExcepion extends Exception{
    Long id;
    String nomeClasse;

    public ItemNotFoundExcepion(Long id, String simpleName) {
        this.id = id;
        this.nomeClasse = simpleName;
    }

}
